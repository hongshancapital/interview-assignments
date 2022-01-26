from aws_cdk import core as cdk

# For consistency with other languages, `cdk` is the preferred import name for
# the CDK's core module.  The following line also imports it as `core` for use
# with examples from the CDK Developer's Guide, which are in the process of
# being updated to use `cdk`.  You may delete this import if you don't need it.
from aws_cdk import core
from aws_cdk import (core, aws_ec2 as ec2, aws_ecs as ecs, aws_iam as iam)
from aws_cdk.aws_ecr_assets import DockerImageAsset
import os.path
import aws_cdk.aws_elasticloadbalancingv2 as elbv2
import aws_cdk.aws_ssm as ssm

class TsEcsFargateStack(cdk.Stack):

    def __init__(self, scope: cdk.Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        # The code that defines your stack goes here

	#############################################
	#Import resorce and custom setting part start
	#############################################
        #cn-north-1
        impRes={
                 "vpc":"vpc-0883083ff3a10c1ec",
                 "SvcSG":"sg-04d3b60e954c1c1ef",
                 "ALBSG":"sg-0b6d093d52d48bba9",
                 "ALBInternet":True,
                 "taskRole":"arn:aws-cn:iam::627484392488:role/ecsTaskExecutionRole",
                 "AlbSubnet":[
                       {"subnetId":"subnet-0d16fa0c969f234d3",
                        "routeTabId":"rtb-074c6b532f3030ad6"},
                       {"subnetId":"subnet-0f28a97c04d3b11cd",
                        "routeTabId":"rtb-074c6b532f3030ad6"}
                 ],
                 #"SvcSubNet":[{"subnetId":"subnet-0d16fa0c969f234d3","routeTabId":"rtb-074c6b532f3030ad6"}]
                 "SvcSubNet":[{"subnetId":"subnet-0f28a97c04d3b11cd","routeTabId":"rtb-0587cc522717461cd"},
                              {"subnetId":"subnet-0d16fa0c969f234d3","routeTabId":"rtb-0587cc522717461cd"}]
               }
        newRes={
                 "TG":{"HealthPath":"/test.html","Port":80,"containPort":80},
                 "Listener":{"Port":80},
                 "TaskFamily":"tsFargate"
               }

        MyTaskDefinition=[{"Cpu":512,"MemLimitMib":1024}]
        MyContainerDefinition=[
             {"containerName":"MyContainer1",
              "cpu":256,
              "essential":True,
              "portMappings":[ecs.PortMapping(container_port=80,host_port=80)], #"portMappings":[ecs.PortMapping(container_port=80,host_port=80),ecs.PortMapping(container_port=22,host_port=22)],
              "environment":{"SSH_PUBLIC_KEY":"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAAAgQC/alWrS+HH5KkPbso+Tsy+Z0WGTX5wvXvon5OacLMyOU3gj2mbbIifasXf/RadpuywuyW3uFirtRlPmSb5Q0PVLODku503Xettw+u6/Z22VV7F2ACgg4iHaCo2SR4L8saUrLLfcKXKr/WCn3w7uYcqGsXEcSFCCSZgn4BoZJqP4Q=="},
              "LogMountPoint":["/usr/local/apache2/logs"]
             }
        ]
        MySvc={"AssignPubIp":True, "desiredCount":1}
	#############################################
	#Import resorce and custom setting part end
	#############################################
		
        #if you import external resource app you cannot set destory policy
        #import VPC, Private Subnet, SG
        vpc = ec2.Vpc.from_lookup(self, "vpc", vpc_id=impRes["vpc"])	
        
        #import SG
        mysvcsg = ec2.SecurityGroup.from_security_group_id(self, "svcsg", 
                impRes["SvcSG"],
                mutable=False)
        
        #import Role
        taskRole = iam.Role.from_role_arn(self, "TaskRole",impRes["taskRole"])
        
        #create ALB        
        mytargetGrp = elbv2.ApplicationTargetGroup(self, "targetGrp",
                target_type=elbv2.TargetType.IP,
                port=newRes["TG"]["Port"],
                vpc=vpc,
                health_check=elbv2.HealthCheck(path=newRes["TG"]["HealthPath"]))
        #target group cannot use .apply_removal_policy directly
        cfn_mytargetGrp=mytargetGrp.node.find_child("Resource")
        cfn_mytargetGrp.apply_removal_policy(cdk.RemovalPolicy.DESTROY)
		
        #import public subnet for alb
        albsubnets = [
                ec2.Subnet.from_subnet_attributes(self,'albsubnetid1',
                    subnet_id = impRes["AlbSubnet"][0]["subnetId"], 
                    route_table_id=impRes["AlbSubnet"][0]["routeTabId"]
                ),
                ec2.Subnet.from_subnet_attributes(self,'albsubnetid2',
                    subnet_id = impRes["AlbSubnet"][1]["subnetId"], 
                    route_table_id=impRes["AlbSubnet"][1]["routeTabId"]
                )
        ]		
        vpc_subnets_selection = ec2.SubnetSelection(subnets=albsubnets)
        #create new ALB
        myalb = elbv2.ApplicationLoadBalancer(self, "ALBv2",
                vpc=vpc,
                security_group=ec2.SecurityGroup.from_security_group_id(self, "ALBSG", impRes["ALBSG"],mutable=False),
                internet_facing=impRes["ALBInternet"],
                vpc_subnets=vpc_subnets_selection)
        myalb.apply_removal_policy(cdk.RemovalPolicy.DESTROY)

        #create new ALB listener
        myalblistener = elbv2.ApplicationListener(self, "ALBlistenter", 
                load_balancer=myalb, 
                port=newRes["Listener"]["Port"])
        myalblistener.apply_removal_policy(cdk.RemovalPolicy.DESTROY)
        myalblistener.add_target_groups("albaddtg", target_groups=[mytargetGrp])
        
        
        #create new ECS Cluster
        mycluster = ecs.Cluster(self, "cluster", vpc=vpc)
        mycluster.apply_removal_policy(cdk.RemovalPolicy.DESTROY)
        
        fargatetaskDefinition = ecs.FargateTaskDefinition(self, "fargatetaskDefinition",
                cpu=MyTaskDefinition[0]["Cpu"],
                memory_limit_mib=MyTaskDefinition[0]["MemLimitMib"],
                execution_role=taskRole,
                family=newRes["TaskFamily"],
                task_role=taskRole)
                #volumes=myEfsVols)      
        fargatetaskDefinition.apply_removal_policy(cdk.RemovalPolicy.DESTROY)
         
        #create container definition for task definition
        MyContainer1def = ecs.ContainerDefinition(self, "MyContainer1def",
                task_definition=fargatetaskDefinition,
                linux_parameters=ecs.LinuxParameters(self,"LinuxPara1",init_process_enabled=True),
                image=ecs.ContainerImage.from_registry("tansong0091/httpd-ssh:0.3"),
                container_name=MyContainerDefinition[0]["containerName"],
                essential=MyContainerDefinition[0]["essential"],
                port_mappings=MyContainerDefinition[0]["portMappings"],
                environment=MyContainerDefinition[0]["environment"]
        )
	
	#import service private subnet
        mysvcprivateSNs = [
                ec2.Subnet.from_subnet_attributes(self,'svcprivateSN1',
                    subnet_id = impRes["SvcSubNet"][0]["subnetId"], 
                    route_table_id=impRes["SvcSubNet"][0]["routeTabId"]),
                ec2.Subnet.from_subnet_attributes(self,'svcprivateSN2',
                    subnet_id = impRes["SvcSubNet"][1]["subnetId"], 
                    route_table_id=impRes["SvcSubNet"][1]["routeTabId"])
        ]

        #create service
        myservice=ecs.FargateService(self,"service",
                task_definition=fargatetaskDefinition,
                assign_public_ip=MySvc["AssignPubIp"],
                platform_version=ecs.FargatePlatformVersion.VERSION1_4,
                vpc_subnets=ec2.SubnetSelection(subnets=mysvcprivateSNs),
                security_group=mysvcsg,
                cluster=mycluster,
                desired_count=MySvc["desiredCount"])
        
        mytargetGrp.add_target(myservice.load_balancer_target(container_name="MyContainer1",container_port=newRes["TG"]["containPort"], protocol=ecs.Protocol.TCP))


 

