#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from '@aws-cdk/core';
import { AwsWafPolicyStack } from '../lib/aws-waf-policy-stack';
import { Waf } from '../lib/aws-waf-policy-stack';

const app = new cdk.App();
new AwsWafPolicyStack(app, 'AwsWafPolicyStack');
//new Waf(this, "devops-waf", {
//  ipAllowList: ["10.0.0.2/32"],
//});
