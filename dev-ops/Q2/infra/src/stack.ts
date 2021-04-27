import * as cdk from '@aws-cdk/core';
import * as ec2 from '@aws-cdk/aws-ec2';
import * as ecr from '@aws-cdk/aws-ecr';
import * as ecs from '@aws-cdk/aws-ecs';
import * as codebuild from '@aws-cdk/aws-codebuild';
import * as elbv2 from '@aws-cdk/aws-elasticloadbalancingv2';
import * as iam from '@aws-cdk/aws-iam'
import * as logs from '@aws-cdk/aws-logs'
import * as lambda from '@aws-cdk/aws-lambda'
import * as dests from '@aws-cdk/aws-logs-destinations'
import * as sns from '@aws-cdk/aws-sns'
import * as codepipeline from '@aws-cdk/aws-codepipeline';
import * as codepipeline_actions from '@aws-cdk/aws-codepipeline-actions';

export class StackContext {
  codeStarConnectionArn: string
}

export class AppInfo {
  name: string
  owner: string
  repo: string
  port: number
  constructor(name: string, owner: string, repo: string, port: number){
    this.name = name
    this.owner = owner
    this.repo = repo
    this.port = port
  }
}

export class BaseStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, context: StackContext, apps:AppInfo[], props: cdk.StackProps) {
    super(scope, id, props);

    // 一些基础设施
    const vpc = new ec2.Vpc(this, 'VPC', {
      cidr: "10.0.0.0/16",
      maxAzs: 2,
      subnetConfiguration: [
        {
          cidrMask: 24,
          name: 'public',
          subnetType: ec2.SubnetType.PUBLIC
        },
        {
          cidrMask: 24,
          name: 'private',
          subnetType: ec2.SubnetType.PRIVATE 
          // subnetType: ec2.SubnetType.PUBLIC // 省钱。注意所有引用subnets的地方应该使用selectSubnets方法以免混淆。
        },
        {
          cidrMask: 24,
          name: 'isolated',
          subnetType: ec2.SubnetType.ISOLATED
        },
      ]
    });

    const ecsCluster = new ecs.Cluster(this, 'Cluster', {
      clusterName: id
    });

    //每个服务共享的负载均衡器、安全组、基础构建镜像等
    const sgAlb = new ec2.SecurityGroup(this, 'sg-alb', {
      securityGroupName: `${id}-alb`,
      vpc: vpc,
    })
    sgAlb.addIngressRule(ec2.Peer.anyIpv4(), ec2.Port.tcp(80))
    sgAlb.addIngressRule(ec2.Peer.anyIpv6(), ec2.Port.tcp(80))
    sgAlb.addIngressRule(ec2.Peer.anyIpv4(), ec2.Port.tcp(443))
    sgAlb.addIngressRule(ec2.Peer.anyIpv6(), ec2.Port.tcp(443))

    const alb = new elbv2.ApplicationLoadBalancer(this, 'alb', {
      loadBalancerName: id,
      vpc: vpc,
      http2Enabled: true,
      internetFacing: true,
      ipAddressType: elbv2.IpAddressType.IPV4,
      securityGroup: sgAlb,
      vpcSubnets: {
        availabilityZones: vpc.availabilityZones,
        // subnets: vpc.publicSubnets
        subnets: vpc.selectSubnets({subnetGroupName:'public'}).subnets
      },
    })

    new cdk.CfnOutput(this, 'output-alb', {
      exportName: 'alb-domain',
      value: alb.loadBalancerDnsName,

    })

    const albListener = new elbv2.ApplicationListener(this, 'albListener', {
      loadBalancer: alb,
      protocol: elbv2.ApplicationProtocol.HTTP,
      // defaultAction: elbv2.ListenerAction.fixedResponse(404)
    })

    const sgApp = new ec2.SecurityGroup(this, 'sg-app', {
      securityGroupName: `${id}-app`,
      vpc: vpc,
      allowAllOutbound: true,
    })
    sgApp.addIngressRule(sgAlb, ec2.Port.allTcp())

    const roleEcsTaskExecution = new iam.Role(this, 'roleEcsTaskExecution', {
      assumedBy: new iam.ServicePrincipal('ecs-tasks.amazonaws.com'),
      managedPolicies: [
        iam.ManagedPolicy.fromAwsManagedPolicyName('service-role/AmazonECSTaskExecutionRolePolicy')
      ]
    })

    const buildPolicy = new iam.Policy(this, `buildpolicy`, {
      statements: [
        new iam.PolicyStatement({
          actions: [
            "ecr:GetAuthorizationToken",
            "ecr:BatchCheckLayerAvailability",
            "ecr:CompleteLayerUpload",
            "ecr:GetAuthorizationToken",
            "ecr:InitiateLayerUpload",
            "ecr:PutImage",
            "ecr:UploadLayerPart",
            "codestar-connections:UseConnection",
          ],
          resources: ['*']
        }),
        // 访问codepipeline的默认S3 Bucket
        new iam.PolicyStatement({
          actions: [
            "s3:GetObject",
            "s3:PutObject",
          ],
          resources: ['arn:aws:s3:::codepipeline*']
        }),
      ]
    })

    const roleCodeDeploy = new iam.Role(this, 'roleCodeDeploy', {
      roleName: 'CodeDeployForECS',
      assumedBy: new iam.ServicePrincipal('codedeploy.amazonaws.com'),
      managedPolicies: [
        iam.ManagedPolicy.fromAwsManagedPolicyName('AWSCodeDeployRoleForECS')
      ]
    })

    //日志相关。所有服务的日志放在统一的日志组中。当然
    const errorSns = new sns.Topic(this, 'errorSns')

    const roleLog2Sns = new iam.Role(this, 'roleLog2Sns', {
      roleName: 'LogToSns',
      assumedBy: new iam.ServicePrincipal('lambda.amazonaws.com'),
      managedPolicies: [
        iam.ManagedPolicy.fromAwsManagedPolicyName('service-role/AWSLambdaBasicExecutionRole'),
      ],
      inlinePolicies: {
        'sns': new iam.PolicyDocument({
          statements: [
            new iam.PolicyStatement({
              actions: [
                "sns:Publish",
              ],
              resources: [errorSns.topicArn]
            })
          ]
        })
      }
    })

    const lambdaLogs2Sns = new lambda.Function(this, 'lambdaLogs2Sns', {
      runtime: lambda.Runtime.PYTHON_3_8,
      handler: 'index.handler',
      code: lambda.Code.fromAsset('./lambda/logs2sns/'),
      role: roleLog2Sns,
      environment: {
        SNS_TOPIC_ARN: errorSns.topicArn
      }
    })
    const logGroup = new logs.LogGroup(this, `logGroup`, {
      logGroupName: `${id}/apps`,
      removalPolicy: cdk.RemovalPolicy.DESTROY,
      retention: logs.RetentionDays.ONE_DAY
    })

    logGroup.addSubscriptionFilter('Error', {
      destination: new dests.LambdaDestination(lambdaLogs2Sns),
      filterPattern: {
        logPatternString: 'Error',
      }
    })

    


    for(const app of apps){
      const imgRepo = new ecr.Repository(this, `repo-${app.name}`, {
        repositoryName: app.name.toLowerCase(),
        removalPolicy: cdk.RemovalPolicy.DESTROY
      });

      //CodeBuild

      const source =  codebuild.Source.gitHub({
        owner: app.owner,
        repo: app.repo
      });

      const build = new codebuild.Project(this, `build-${app.name}`, {
        source: source,
        projectName: app.name,
        environment:{
          buildImage: codebuild.LinuxBuildImage.AMAZON_LINUX_2_3,
          privileged: true,
          environmentVariables:{
            TARGET_IMAGE_NAME: {value: imgRepo.repositoryName},
            PUSH_TO_ECR: {value: 'true'}
          }
        }, 
        buildSpec: codebuild.BuildSpec.fromObject({
          version: '0.2',
          phases: {
            install: {
              'runtime-versions': {
                java: 'corretto11'
              } 
            },
            build: { 
              commands: [
                './build.sh'
              ],
            },
          },
          artifacts: {
            files: [
              'imagedefinitions.json'
            ]
          },
          cache: {
            paths: [
              '/root/.m2/**/*'
            ]
          }
        }),
      });

      build.role?.attachInlinePolicy(buildPolicy)
  
      //ECS
      const taskDef = new ecs.FargateTaskDefinition(this, `taskDef-${app.name}`,{
        memoryLimitMiB: 1024,
        executionRole: roleEcsTaskExecution,

      })

      // logGroup.addSubscriptionFilter('ERROR', {
      //   destination: new dests.LambdaDestination(lambdaLogs2Sns),
      //   filterPattern: {
      //     logPatternString: 'ERROR',
      //   }
      // })
  
      taskDef.addContainer('main', {
        // image: new ecs.EcrImage(ecrRepo, 'latest'),
        image: ecs.ContainerImage.fromRegistry('tomcat:latest'),
        portMappings: [{
          containerPort: app.port,
          protocol: ecs.Protocol.TCP
        }],
        logging: new ecs.AwsLogDriver({
          streamPrefix: `${app.name}/`,
          logGroup: logGroup
        })
      })

      const service = new ecs.FargateService(this, `service-${app.name}`, {
        serviceName: app.name,
        taskDefinition: taskDef,
        cluster: ecsCluster,
        vpcSubnets: vpc.selectSubnets({subnetGroupName: 'private'}),
        securityGroup: sgApp,
        
        // assignPublicIp: true, // 因为部署在了public子网，因此必须分配publicIP才能访问Internet
      })

      service.registerLoadBalancerTargets({
        newTargetGroupId: `${app.name}-${app.port}`,
        containerName: 'main',
        containerPort: app.port,
        listener: ecs.ListenerConfig.applicationListener(albListener),
      })

      //CodePipeline
      const sourceArtifact = new codepipeline.Artifact('SourceArtifact')
      const buildArtifact = new codepipeline.Artifact('BuildArtifact')
      const pipeline = new codepipeline.Pipeline(this, `pipeline-${app.name}`, {
        pipelineName: `${id}-${app.name}`,
        stages: [
          {
            stageName: 'Source',
            actions: [
              new codepipeline_actions.CodeStarConnectionsSourceAction({
                actionName: 'Source',
                connectionArn: context.codeStarConnectionArn,
                repo: app.repo,
                owner: app.owner,
                output: sourceArtifact,
                codeBuildCloneOutput: true
              })
            ]
          },
          {
            stageName: 'Build',
            actions:[
              new codepipeline_actions.CodeBuildAction({
                actionName: 'Build',
                project: build,
                input: sourceArtifact,
                outputs: [buildArtifact],
              })
            ]
          },
          {
            stageName: 'Deploy',
            actions: [
              new codepipeline_actions.EcsDeployAction({
                actionName: 'Deploy',
                service: service,
                input: buildArtifact,
              })
            ]
          }
        ]
      })

      
    }
  }
}