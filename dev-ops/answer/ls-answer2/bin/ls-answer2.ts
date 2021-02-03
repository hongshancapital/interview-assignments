#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from '@aws-cdk/core';
import { LsAnswer2Stack } from '../lib/ls-answer2-stack';

const app = new cdk.App();
new LsAnswer2Stack(app, 'LsAnswer2Stack');
