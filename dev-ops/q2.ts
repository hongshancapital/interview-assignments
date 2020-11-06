import * as cdk from '@aws-cdk/core';
import * as ec2 from "@aws-cdk/aws-ec2";
import * as ecs from "@aws-cdk/aws-ecs";
import * as ecs_patterns from "@aws-cdk/aws-ecs-patterns";

export class Q2Stack extends cdk.Stack {

  image: string = "amazon/amazon-ecs-sample";

  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    // VPC
    const vpc = new ec2.Vpc(this, "Q2Vpc", {
      maxAzs: 3
    });

    // ECS cluster
    const cluster = new ecs.Cluster(this, "Q2Cluster", {
      vpc: vpc
    });

    // Create ALB
    new ecs_patterns.ApplicationLoadBalancedFargateService(this, "Q2FargateService", {
      cluster: cluster,
      taskImageOptions: { image: ecs.ContainerImage.fromRegistry(this.image) }
    });
  }
}
