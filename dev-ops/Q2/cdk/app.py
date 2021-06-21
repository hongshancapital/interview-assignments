from aws_cdk import (
    aws_ec2 as ec2,
    aws_ecs as ecs,
    aws_ecs_patterns as ecs_patterns,
    core,
)


class AutoScalingFargateService(core.Stack):

    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        # Create a cluster
        vpc = ec2.Vpc(
            self, "Vpc",
            max_azs=2
        )

        cluster = ecs.Cluster(
            self, 'fargate-service-autoscaling',
            vpc=vpc,
            container_insights=True
        )

        # # Create task definition
        # task_definition = ecs.FargateTaskDefinition(
        #     self, "fargate-task-definition"
        # )

        # image
        image_options = ecs_patterns.NetworkLoadBalancedTaskImageOptions(
            image=ecs.ContainerImage.from_registry("lolspider/spring-boot-hello:master-8"),
            container_name="spring-boot-hello-container",
            container_port=8080,
            log_driver=ecs.LogDriver.aws_logs(stream_prefix="spring-boot-hello")
        )

        # Create Fargate Service
        fargate_service = ecs_patterns.NetworkLoadBalancedFargateService(
            self, "spring-boot-hello",
            cluster=cluster,
            # task_definition=task_definition,
            cpu=0.5,
            memory_limit_mib=1024,
            listener_port=8080,
            task_image_options=image_options
        )

        fargate_service.service.connections.security_groups[0].add_ingress_rule(
            peer=ec2.Peer.ipv4(vpc.vpc_cidr_block),
            connection=ec2.Port.tcp(8080),
            description="Allow http inbound from VPC"
        )

        # Setup AutoScaling policy
        scaling = fargate_service.service.auto_scale_task_count(
            max_capacity=2
        )
        scaling.scale_on_cpu_utilization(
            "CpuScaling",
            target_utilization_percent=50,
            scale_in_cooldown=core.Duration.seconds(60),
            scale_out_cooldown=core.Duration.seconds(60),
        )

        # # define container definition
        # ecs.ContainerDefinition(
        #     self, "spring-boot-hello-container",
        #     task_definition=task_definition,
        #     logging=ecs.LogDriver.aws_logs("spring-boot-hello")
        # )

        core.CfnOutput(
            self, "LoadBalancerDNS",
            value=fargate_service.load_balancer.load_balancer_dns_name
        )


app = core.App()
AutoScalingFargateService(app, "aws-fargate-application-autoscaling")
app.synth()
