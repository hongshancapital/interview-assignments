#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from '@aws-cdk/core';
import * as stack from './stack';

console.log('CODE_STAR_CONNECTION_ARN', process.env['CODE_STAR_CONNECTION_ARN'])
console.log('GITHUB_OWNER', process.env['GITHUB_OWNER'])

const app = new cdk.App();
const prop = {
}
const context = new stack.StackContext()
context.codeStarConnectionArn = process.env['CODE_STAR_CONNECTION_ARN'] as string

const apps = [new stack.AppInfo('spring-boot-testing-strategies', process.env['GITHUB_OWNER'] as string, 'spring-boot-testing-strategies', 8080)]

new stack.BaseStack(app, 'Q2', context, apps, {});