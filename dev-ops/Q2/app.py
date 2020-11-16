from aws_cdk import (
    aws_ec2 as ec2,
    aws_ecs as ecs,
    aws_ecs_patterns as ecs_patterns,
    core,
)


class EcsFargate(core.Stack):

    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, *kwargs)

        # Create VPC with private subnet and Fargate Cluster
        # NOTE: Limit AZs to avoid reaching resource quotas
        vpc = ec2.Vpc(self, "Vpc",
            cidr="10.0.0.0/21",
            max_azs=2,
            subnet_configuration=[ec2.SubnetConfiguration(
                cidr_mask=24,
                name="Application",
                subnet_type=ec2.SubnetType.PRIVATE
            ), ec2.SubnetConfiguration(
                cidr_mask=24,
                name="Ingress",
                subnet_type=ec2.SubnetType.PUBLIC
            )
            ]
        )

        cluster = ecs.Cluster(
            self, 'Ec2Cluster',
            vpc=vpc
        )

        fargate_service = ecs_patterns.ApplicationLoadBalancedFargateService(
            self, "FargateService",
            assign_public_ip=False,
            cluster=cluster,
            public_load_balancer=True,
            task_image_options={
                'image': ecs.ContainerImage.from_registry("amazon/amazon-ecs-sample")
            }
        )

        fargate_service.service.connections.security_groups[0].add_ingress_rule(
            peer = ec2.Peer.ipv4(vpc.vpc_cidr_block),
            connection = ec2.Port.tcp(80),
            description="Allow http inbound from VPC"
        )

        core.CfnOutput(
            self, "LoadBalancerDNS",
            value=fargate_service.load_balancer.load_balancer_dns_name
        )

app = core.App()
EcsFargate(app, "PaaS")
app.synth()
