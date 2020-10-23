#!/usr/bin/env python3

from aws_cdk import core

from aws_project.aws_project_stack import AwsProjectStack


app = core.App()
AwsProjectStack(app, "aws-project")

app.synth()
