import * as cdk from '@aws-cdk/core';
import * as ec2 from "@aws-cdk/aws-ec2";
import * as ecs from "@aws-cdk/aws-ecs";
import * as ecs_patterns from "@aws-cdk/aws-ecs-patterns";

export class AwsEcsFargateServiceStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    //defines the stack
    const vpc = new ec2.Vpc(this, "EcsFargateVpc", {
      maxAzs: 2,
      natGateways: 1,
      cidr: '20.10.0.0/16',
      subnetConfiguration: [
                {
                    cidrMask: 24,
                    name: 'Public',
                    subnetType: ec2.SubnetType.PUBLIC,
                },
                {
                    cidrMask: 24,
                    name: 'Private-ECS',
                    subnetType: ec2.SubnetType.PRIVATE,
                },
            ]
    });

    const selection = vpc.selectSubnets({
        subnetType: ec2.SubnetType.PRIVATE
    });

    const cluster = new ecs.Cluster(this, "ECSFargateCluster", {
      vpc: vpc,
      clusterName: "EcsFargate-cluster"
    });

    // Create a load-balanced Fargate service and make it public
    new ecs_patterns.ApplicationLoadBalancedFargateService(this, "MyFargateService", {
      cluster: cluster, 
      desiredCount: 2, 
      taskImageOptions: { image: ecs.ContainerImage.fromRegistry("amazon/amazon-ecs-sample") }
    });
  }
}