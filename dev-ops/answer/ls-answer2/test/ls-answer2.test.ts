import { expect as expectCDK, matchTemplate, MatchStyle } from '@aws-cdk/assert';
import * as cdk from '@aws-cdk/core';
import * as LsAnswer2 from '../lib/ls-answer2-stack';

test('Empty Stack', () => {
    const app = new cdk.App();
    // WHEN
    const stack = new LsAnswer2.LsAnswer2Stack(app, 'MyTestStack');
    // THEN
    expectCDK(stack).to(matchTemplate({
      "Resources": {}
    }, MatchStyle.EXACT))
});
