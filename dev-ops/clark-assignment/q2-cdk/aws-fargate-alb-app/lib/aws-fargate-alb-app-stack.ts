import * as cdk from '@aws-cdk/core';
import * as ec2 from "@aws-cdk/aws-ec2";
import * as ecs from "@aws-cdk/aws-ecs";
import * as ecs_patterns from "@aws-cdk/aws-ecs-patterns";

export class AwsFargateAlbAppStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    //The code that defines your stack goes here
    const vpc = new ec2.Vpc(this, 'aws-devops-ecs-vpc', {
            maxAzs: 3,
            //网关设置为0时，会默认创建public和istiolated网络
            //natGateways: 1,
            //vpnGateway: true,
            subnetConfiguration: [
                {
                    name: "aws-devops-pri",
                    cidrMask: 24,
                    subnetType: ec2.SubnetType.PRIVATE,
                },
                {
                    name: "aws-devops-pub",
                    cidrMask: 24,
                    subnetType: ec2.SubnetType.PUBLIC,
                }//,
                //{
                //    name: "aws-devops-isolate",
                //    cidrMask: 24,
                //    subnetType: ec2.SubnetType.ISOLATED,
                //}
            ]
        });
    
    //const vpc = new ec2.Vpc(this, "aws-devops-ecs-vpc",{ 
    //  maxAzs: 3, // Default is all AZs in region
    //}
    //);

    const cluster = new ecs.Cluster(this, "aws-devops-ecs-cluster", {
      vpc: vpc
    });

    // Create a load-balanced Fargate service and make it public/private
    new ecs_patterns.ApplicationLoadBalancedFargateService(this, "DevOpsFargateService", {
      cluster: cluster, // Required
      cpu: 256, // Default is 256
      desiredCount: 6, // Default is 1
      taskImageOptions: { image: ecs.ContainerImage.fromRegistry("amazon/amazon-ecs-sample") },
      memoryLimitMiB: 512, // Default is 512
      publicLoadBalancer: false// Default is false
    });

  }
}
