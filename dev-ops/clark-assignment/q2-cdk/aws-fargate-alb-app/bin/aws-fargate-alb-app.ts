#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from '@aws-cdk/core';
import { AwsFargateAlbAppStack } from '../lib/aws-fargate-alb-app-stack';

const app = new cdk.App();
new AwsFargateAlbAppStack(app, 'AwsFargateAlbAppStack');
