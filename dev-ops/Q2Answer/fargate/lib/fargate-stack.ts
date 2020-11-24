import * as cdk from '@aws-cdk/core';
import * as ec2 from "@aws-cdk/aws-ec2";
import * as ecs from "@aws-cdk/aws-ecs";
import * as ecs_patterns from "@aws-cdk/aws-ecs-patterns";
import * as waf from "@aws-cdk/aws-wafv2";

export class FargateStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    // The code that defines your stack goes here
    const vpc = new ec2.Vpc(this, "MyVpc", {
          maxAzs: 3 // Default is all AZs in region
      }
    );
      
    const cluster = new ecs.Cluster(this, "MyCluster", {
        vpc: vpc
    });
    
    // Create a load-balanced Fargate service and make it internal
    const farget = new ecs_patterns.ApplicationLoadBalancedFargateService(this, "MyFargateService", {
        cluster: cluster, // Required
        cpu: 512, // Default is 256
        desiredCount: 1, // Default is 1
        taskImageOptions: { image: ecs.ContainerImage.fromRegistry("amazon/amazon-ecs-sample") },
        memoryLimitMiB: 1024, // Default is 512
        publicLoadBalancer: false, // Default is false
        assignPublicIp: false
    });

    const myWaf = new waf.CfnWebACL(this, "MyALBWaf", {
      defaultAction: { 
        allow: {}, 
      },
      name: "MyWebACL",
      scope: "REGIONAL",
      visibilityConfig: {
        cloudWatchMetricsEnabled : true,
        metricName : "MyMetriName",
        sampledRequestsEnabled : false
      },
    });

    const associates = new waf.CfnWebACLAssociation(this, "AlbWafAssocication", {
      resourceArn: farget.loadBalancer.loadBalancerArn,
      webAclArn: cdk.Fn.getAtt(myWaf.logicalId, "Arn").toString(),
    });
  }
}