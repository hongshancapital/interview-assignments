#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from '@aws-cdk/core';
import { FargateStack } from '../lib/fargate-stack';

import * as ec2 from "@aws-cdk/aws-ec2";
import * as ecs from "@aws-cdk/aws-ecs";
import * as ecs_patterns from "@aws-cdk/aws-ecs-patterns";

const app = new cdk.App();
const fgstack = new FargateStack(app, 'FargateStack');

const vpc = new ec2.Vpc(fgstack, "MyVpc", {
    maxAzs: 3 // Default is all AZs in region
});

const cluster = new ecs.Cluster(fgstack, "MyCluster", {
    vpc: vpc
});

// Create a load-balanced Fargate service and make it public
new ecs_patterns.ApplicationLoadBalancedFargateService(fgstack, "MyFargateService", {
    cluster: cluster, // Required
    cpu: 512, // Default is 256
    desiredCount: 1, // Default is 1
    taskImageOptions: { image: ecs.ContainerImage.fromRegistry("amazon/amazon-ecs-sample") },
    memoryLimitMiB: 1024, // Default is 512
    publicLoadBalancer: false, // Default is false
    assignPublicIp: false
});