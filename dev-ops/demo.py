# aws Fargate 自动创建
from aws_cdk import core as cdk

import aws_cdk.aws_ecs as ecs
from aws_cdk import aws_apigatewayv2 as api
import aws_cdk.aws_ecs_patterns as ecsp

class SimplestSpringBootHelloWorld(cdk.Stack):
	def __init__(self, scope: cdk.Construct, construct_id: str, **kwargs) -> None:
		super().__init__(scope, construct_id, **kwargs)

		ecsp.ApplicationLoadBalancedFargateService(self, "Simplest-Spring-Boot-Hello-World",
            task_image_options=ecsp.ApplicationLoadBalancedTaskImageOptions(
                image=ecs.ContainerImage.from_registry("Simplest-Spring-Boot-Hello-World")),
            public_load_balancer=True
        )

# 分开创建
class SimplestSpringBootHelloWorld(cdk.Stack):

    def __init__(self, scope: cdk.Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        # 创建VPC
        vpc = ec2.Vpc(self, "VPC",
            nat_gateways=0,
            subnet_configuration=[ec2.SubnetConfiguration(name="ingress",subnet_type=ec2.SubnetType.PUBLIC,cidr_mask=24)]
            )

        # 定义系统镜像
        amzn_linux = ec2.MachineImage.latest_amazon_linux(
            generation=ec2.AmazonLinuxGeneration.AMAZON_LINUX_2,
            edition=ec2.AmazonLinuxEdition.STANDARD,
            virtualization=ec2.AmazonLinuxVirt.HVM,
            storage=ec2.AmazonLinuxStorage.GENERAL_PURPOSE
            )

        # 创建安全规则
        role = iam.Role(self, "InstanceSSM", assumed_by=iam.ServicePrincipal("ec2.amazonaws.com"))

        role.add_managed_policy(iam.ManagedPolicy.from_aws_managed_policy_name("AmazonSSMManagedInstanceCore"))

        role = iam.Role(self, "InstanceSSM", assumed_by=iam.ServicePrincipal("ecs-tasks.amazonaws.com"))

        role.add_managed_policy(iam.ManagedPolicy.fromAwsManagedPolicyName('service-role/AmazonECSTaskExecutionRolePolicy'))


        # 创建ECS实例
        instance = ec2.Instance(self, "Instance",
            instance_type=ec2.InstanceType("t3.nano"),
            machine_image=amzn_linux,
            vpc = vpc,
            role = role,
            securityGroup = bastionSecGrp,
      		keyName =  "ssh-key",
            )

        # 创建容器服务
        demo = ecs.FargateTaskDefinition(self, "demo",
       		memoryLimitMiB = 512,
       		cpu = 256,
       		taskRole = taskrole
       	)

       	# 创建 svc
       	demo_svc = ecs.FargateService(self, "demo",
       		cluster = cluster,
      		taskDefinition = demo,
      		assignPublicIp: false,
      		desiredCount: 2,
      		securityGroup: bastionSecGrp,
      		cloudMapOptions: {
        		name: 'demo'
      		},
       	)

        # 获取初始化脚本
        asset = Asset(self, "Asset", path=os.path.join(dirname, "configure.sh"))
        local_path = instance.user_data.add_s3_download_command(
            bucket=asset.bucket,
            bucket_key=asset.s3_object_key
        )

        # 定义执行命令
        instance.user_data.add_execute_file_command(
            file_path=local_path
            )
        asset.grant_read(instance.role)