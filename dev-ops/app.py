#!/usr/bin/env python3
import os

from aws_cdk import core as cdk

# For consistency with TypeScript code, `cdk` is the preferred import name for
# the CDK's core module.  The following line also imports it as `core` for use
# with examples from the CDK Developer's Guide, which are in the process of
# being updated to use `cdk`.  You may delete this import if you don't need it.
from aws_cdk import core

from ts_ecs_fargate.ts_ecs_fargate_stack import TsEcsFargateStack
from aws_cdk.core import Tags

app = core.App()
TsEcsFargateStack(app, "TsEcsFargateStack",
    # If you don't specify 'env', this stack will be environment-agnostic.
    # Account/Region-dependent features and context lookups will not work,
    # but a single synthesized template can be deployed anywhere.

    # Uncomment the next line to specialize this stack for the AWS Account
    # and Region that are implied by the current CLI configuration.

    #env=core.Environment(account=os.getenv('CDK_DEFAULT_ACCOUNT'), region=os.getenv('CDK_DEFAULT_REGION')),

    # Uncomment the next line if you know exactly what Account and Region you
    # want to deploy the stack to. */

    env=core.Environment(account='YOUR_AWS_ACCOUNT', region='cn-north-1'),

    # For more information, see https://docs.aws.amazon.com/cdk/latest/guide/environments.html
    )
#Tag stack and all constructs under it
Tags.of(app).add("env","tstest")
Tags.of(app).add(key="app", value="tsFargate")
Tags.of(app).add(key="desc", value="AWS CDK generated")

app.synth()
