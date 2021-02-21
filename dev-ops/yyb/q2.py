#!/usr/bin/python3

from aws_cdk import core 
from aws_project.aws_project_stack import AwsProjectStack
from aws_cdk import (core, aws_ec2 as ec2, aws_ecs as ecs, aws_ecs_patterns as ecs_patterns)

class AwsFargateTest(core.Stack):
    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)
        vpc = ec2.Vpc(self, "MyVpc", max_azs=3)     # default is all AZs in region
        cluster = ecs.Cluster(self, "MyCluster", vpc=vpc)
        ecs_patterns.ApplicationLoadBalancedFargateService(self, "MyFargateService",
            cluster=cluster,            # Required
            cpu=512,                    # Default is 256
            desired_count=6,            # Default is 1
            task_image_options=ecs_patterns.ApplicationLoadBalancedTaskImageOptions(
                image=ecs.ContainerImage.from_registry("amazon/amazon-ecs-sample")),
            memory_limit_mib=2048,      # Default is 512
            public_load_balancer=False)  # Default is False

app = core.App()
AwsFargateTest(app, "fargate-test")
app.synth()
