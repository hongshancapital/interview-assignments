import { expect as expectCDK, matchTemplate, MatchStyle } from '@aws-cdk/assert';
import * as cdk from '@aws-cdk/core';
import * as Fargate from '../lib/fargate-stack';

test('Empty Stack', () => {
    const app = new cdk.App();
    // WHEN
    const stack = new Fargate.FargateStack(app, 'MyTestStack');
    // THEN
    expectCDK(stack).to(matchTemplate({
      "Resources": {}
    }, MatchStyle.EXACT))
});
