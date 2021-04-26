from aws_cdk import (
    core,
    aws_cloudwatch as cloudwatch,
    aws_cloudwatch_actions as cloudwatch_actions,
    aws_ecs as ecs,
    aws_ecr as ecr,
    aws_ec2 as ec2,
    aws_iam as iam,
    aws_logs as logs,
    aws_sns as sns,
)


PREFIX = 'devops-assignment-q2'
SNS_EMAIL = 'spengjie@sina.com'


class Q2AwsCdkStack(core.Stack):

    def __init__(self, scope: core.Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        # Create the ECR Repository
        ecr.Repository(
           self,
           f'{PREFIX}-repository',
           repository_name=f'{PREFIX}-repository',
        )

        # Create the ECS Cluster (and VPC)
        vpc = ec2.Vpc(
            self,
            f'{PREFIX}-vpc',
            nat_gateways=0,
            subnet_configuration=[
                ec2.SubnetConfiguration(name='public',
                                        subnet_type=ec2.SubnetType.PUBLIC)
            ],
            max_azs=3,
        )
        security_group = ec2.SecurityGroup(
            self,
            f'{PREFIX}-security-group',
            vpc=vpc,
        )
        security_group.add_ingress_rule(
            peer=ec2.Peer.ipv4('0.0.0.0/0'),
            connection=ec2.Port.tcp(5000),
            description='Allow http inbound from anywhere',
        )
        cluster = ecs.Cluster(
            self,
            f'{PREFIX}-cluster',
            cluster_name=f'{PREFIX}-cluster',
            vpc=vpc,
        )

        # Create the ECS Task Definition (and named Task Execution IAM Role)
        execution_role = iam.Role(
            self,
            f'{PREFIX}-ecs-execution-role',
            assumed_by=iam.ServicePrincipal('ecs-tasks.amazonaws.com'),
            role_name=f'{PREFIX}-ecs-execution-role')
        execution_role.add_to_policy(iam.PolicyStatement(
            effect=iam.Effect.ALLOW,
            resources=['*'],
            actions=[
                'ecr:GetAuthorizationToken',
                'ecr:BatchCheckLayerAvailability',
                'ecr:GetDownloadUrlForLayer',
                'ecr:BatchGetImage',
                'logs:CreateLogStream',
                'logs:PutLogEvents'
            ],
        ))
        task_definition = ecs.FargateTaskDefinition(
            self,
            f'{PREFIX}-task-definition',
            execution_role=execution_role,
            family=f'{PREFIX}-task-definition',
        )

        # Send logs to CloudWatch and send email for error logs
        log_group = logs.LogGroup(
            self,
            f'{PREFIX}-logs',
            log_group_name=f'{PREFIX}-logs',
        )
        metric = cloudwatch.Metric(
            metric_name=f'{PREFIX}-error-logs-metric',
            namespace=f'{PREFIX}-error-logs-ns',
            period=core.Duration.minutes(5),
        )
        log_group.add_metric_filter(
            f'{PREFIX}-error-logs',
            filter_pattern=logs.FilterPattern.literal('ERROR'),
            metric_name=f'{PREFIX}-error-logs-metric',
            metric_namespace=f'{PREFIX}-error-logs-ns',
        )
        sns_topic = sns.Topic(
            self,
            f'{PREFIX}-topic',
            topic_name=f'{PREFIX}-topic',
        )
        sns.Subscription(
            self,
            f'{PREFIX}-subscription',
            topic=sns_topic,
            endpoint=SNS_EMAIL,
            protocol=sns.SubscriptionProtocol.EMAIL,
        )
        alarm = cloudwatch.Alarm(
            self,
            f'{PREFIX}-error-log-alarm',
            metric=metric,
            threshold=1,
            evaluation_periods=1,
            statistic='sum',
            alarm_name=f'{PREFIX}-error-log-alarm',
        )
        alarm.add_alarm_action(cloudwatch_actions.SnsAction(sns_topic))

        # Add Container Definition for ECS Task Definition
        container = task_definition.add_container(
            f'{PREFIX}-app',
            image=ecs.ContainerImage.from_registry(f'{PREFIX}-repository'),
            logging=ecs.LogDriver.aws_logs(
                stream_prefix='ecs',
                log_group=log_group,
            )
        )
        port_mapping = ecs.PortMapping(
            container_port=5000,
            protocol=ecs.Protocol.TCP,
        )
        container.add_port_mappings(port_mapping)

        # Create the ECS Service
        ecs.FargateService(
            self,
            f'{PREFIX}-service',
            cluster=cluster,
            task_definition=task_definition,
            security_groups=[security_group],
            assign_public_ip=True,
            service_name=f'{PREFIX}-service',
        )
