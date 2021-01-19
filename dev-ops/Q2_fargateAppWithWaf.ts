#!/usr/bin/env node
import * as cdk from '@aws-cdk/core';
import ec2 = require('@aws-cdk/aws-ec2');
import ecs = require('@aws-cdk/aws-ecs');
import ecs_patterns = require('@aws-cdk/aws-ecs-patterns');
import * as wafv2 from "@aws-cdk/aws-wafv2";

class FargateAppWithWaf extends cdk.Stack {
    constructor(scope: cdk.App, id: string, props?: cdk.StackProps) {
        super(scope, id, props);
        
        // Create VPC and Fargate Cluster
        // NOTE: Limit AZs to avoid reaching resource quotas
        const vpc = new ec2.Vpc(this, 'MyVpc', { maxAzs: 2 });
        const cluster = new ecs.Cluster(this, 'Cluster', { vpc });
        
        // Instantiate Fargate Service with just cluster and image
        const fargateApp = new ecs_patterns.ApplicationLoadBalancedFargateService(this, "FargateService", {
            cluster,
            taskImageOptions: {
                image: ecs.ContainerImage.fromRegistry("amazon/amazon-ecs-sample"),
            },
            // no public ip assigned
            assignPublicIp: false,
        });

        const wafIPSet = new wafv2.CfnIPSet(this, 'TestWafIPSet', {
            name: 'TestWafIpSet',
            ipAddressVersion: 'IPV4',
            scope: 'REGIONAL',
            addresses: ['192.168.0.1/32']
        });

        const webAcl = new wafv2.CfnWebACL(this, 'TestWebAcl', {
            defaultAction: { block: {} },
            name: 'TestWafWebAcl',
            scope: 'REGIONAL',
            visibilityConfig: {
              cloudWatchMetricsEnabled: true,
              sampledRequestsEnabled: true,
              metricName: 'TestWafWebAcl',
            },
            rules: [
              {
                priority: 1,
                name: 'TestWafWebAclIpSetRule',
                action: { allow: {} },
                visibilityConfig: {
                  sampledRequestsEnabled: true,
                  cloudWatchMetricsEnabled: true,
                  metricName: 'TestWafWebAclIpSetRule',
                },
                statement: {
                  ipSetReferenceStatement: {
                    arn: wafIPSet.attrArn,
                  },
                },
              },
            ],
        });

        const association = new wafv2.CfnWebACLAssociation(this, 'TestWebAclAssociation', {
            resourceArn: fargateApp.loadBalancer.loadBalancerArn,
            webAclArn: webAcl.attrArn
        });
    }
}

const app = new cdk.App();

new FargateAppWithWaf(app, 'fargate-app-01');

app.synth();