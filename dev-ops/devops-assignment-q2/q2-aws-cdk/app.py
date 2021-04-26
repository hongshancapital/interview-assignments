#!/usr/bin/env python3

from aws_cdk import core

from q2_aws_cdk.q2_aws_cdk_stack import Q2AwsCdkStack


app = core.App()
Q2AwsCdkStack(app, 'q2-aws-cdk')

app.synth()
