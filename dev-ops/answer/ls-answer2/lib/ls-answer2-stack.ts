import * as cdk from "@aws-cdk/core";
import * as ec2 from "@aws-cdk/aws-ec2";
import * as ecs from "@aws-cdk/aws-ecs";
import * as ecs_patterns from "@aws-cdk/aws-ecs-patterns";
import * as waf from "@aws-cdk/aws-wafv2";
import * as ecr from "@aws-cdk/aws-ecr";

export class LsAnswer2Stack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const vpc = new ec2.Vpc(this, "LsVpc", {
      subnetConfiguration: [
        {
          cidrMask: 24,
          name: "Answer2",
          subnetType: ec2.SubnetType.ISOLATED,
        }
      ],
      maxAzs: 2,
    });

    new ec2.GatewayVpcEndpoint(this, "LsS3", {
      vpc: vpc,
      service: {
        name: "com.amazonaws.us-west-2.s3"
      },
    });

    new ec2.InterfaceVpcEndpoint(this, "LsEndEcrDkr", {
      vpc: vpc,
      service: {
        name: "com.amazonaws.us-west-2.ecr.dkr",
        port: 443,
      },
    });

    new ec2.InterfaceVpcEndpoint(this, "LsEndEcrApi", {
      vpc: vpc,
      service: {
        name: "com.amazonaws.us-west-2.ecr.api",
        port: 443,
      },
    });

    new ec2.InterfaceVpcEndpoint(this, "LsEndLogs", {
      vpc: vpc,
      service: {
        name: "com.amazonaws.us-west-2.logs",
        port: 443,
      },
    });

    const cluster = new ecs.Cluster(this, "LsCluster", {
      vpc: vpc
    });

    //const repo = ecr.Repository.fromRepositoryName(this, "LsRepo", "878795919716.dkr.ecr.us-west-2.amazonaws.com/amazon-ecs-sample")
    const repo = ecr.Repository.fromRepositoryName(this, "LsRepo", "amazon-ecs-sample")

    const fargate = new ecs_patterns.ApplicationLoadBalancedFargateService(this, "LsFargateService", {
      cluster: cluster,
      cpu: 256,
      desiredCount: 1,
      //taskImageOptions: { image: ecs.ContainerImage.fromRegistry("amazon/amazon-ecs-sample") },
      taskImageOptions: { 
        //image: ecs.ContainerImage.fromRegistry("878795919716.dkr.ecr.us-west-2.amazonaws.com/amazon-ecs-sample:latest") 
        image: ecs.ContainerImage.fromEcrRepository(repo)
      },
      publicLoadBalancer: false
    });

    const webacl = new waf.CfnWebACL(this, "LsAcl", {
      name: "Answer2",
      scope: "REGIONAL",
      defaultAction: { allow: {} },
      rules: [
        {
          name: "PHP",
          priority: 1,
          statement: {
            managedRuleGroupStatement: {
              name: "AWSManagedRulesPHPRuleSet",
              vendorName: "AWS",
            },
          },
          overrideAction: {
            count: {}
          },
          visibilityConfig: {
            cloudWatchMetricsEnabled: false,
            metricName: "PHP",
            sampledRequestsEnabled: true,
          },
        },
      ],
      visibilityConfig: {
        cloudWatchMetricsEnabled: false,
        metricName: "Answer2",
        sampledRequestsEnabled: true,
      },
    });
 
    new waf.CfnWebACLAssociation(this, "LsAsso", {
      webAclArn: webacl.attrArn,
      resourceArn: fargate.loadBalancer.loadBalancerArn,
    });

  }
}

