import * as cdk from '@aws-cdk/core';
import * as ec2 from '@aws-cdk/aws-ec2';
import * as ecs from '@aws-cdk/aws-ecs';
import * as ecs_patterns from '@aws-cdk/aws-ecs-patterns';
import * as wafv2 from '@aws-cdk/aws-wafv2';
import { Subnet, SubnetType } from '@aws-cdk/aws-ec2';

export class MyEcsConstructStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const vpc = new ec2.Vpc(this, "MyVpc", {
      maxAzs: 3,
      enableDnsHostnames: false,
    })
    const cluster = new ecs.Cluster(this, "MyCluster", {vpc: vpc})
    const fargate = new ecs_patterns.ApplicationLoadBalancedFargateService(this, "MyFargateService", {
      cluster: cluster,
      cpu: 512,
      desiredCount: 6,
      taskImageOptions: {
        image: ecs.ContainerImage.fromRegistry("amazon/amazon-ecs-sample"),
      },
      memoryLimitMiB: 2048,
      publicLoadBalancer: true,
      assignPublicIp: false,
      taskSubnets: {
        subnetType: SubnetType.PRIVATE,
      }
    })
    const webAcl = new wafv2.CfnWebACL(this, "MyWebAcl", {
      defaultAction: { allow: {} },
      scope: "REGIONAL",
      visibilityConfig: {
          sampledRequestsEnabled: true,
          cloudWatchMetricsEnabled: true,
          metricName: "mywebacl-metrics",
      },
      rules: [
        {
          name: "rate-limit-rule",
          priority: 0,
          statement: {
            rateBasedStatement: {
              limit: 100,
              aggregateKeyType: "IP",
            }
          },
          action: { block: {} },
          visibilityConfig: {
            sampledRequestsEnabled: true,
            cloudWatchMetricsEnabled: true,
            metricName: "mywebacl-rate-limit-rule-metrics",
          },
        },
      ],
    })
    new wafv2.CfnWebACLAssociation(this, "MyWebAclAssociation", {
      webAclArn: webAcl.attrArn,
      resourceArn: fargate.loadBalancer.loadBalancerArn,
    })
  }
}
