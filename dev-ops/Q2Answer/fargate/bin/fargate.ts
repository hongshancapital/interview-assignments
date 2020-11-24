#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from '@aws-cdk/core';
import { FargateStack } from '../lib/fargate-stack';

const app = new cdk.App();
new FargateStack(app, 'FargateStack');