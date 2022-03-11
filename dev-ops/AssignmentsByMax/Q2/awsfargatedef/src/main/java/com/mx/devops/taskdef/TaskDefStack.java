package com.mx.devops.taskdef;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Peer;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.AwsLogDriverProps;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerDefinition;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.FargatePlatformVersion;
import software.amazon.awscdk.services.ecs.FargateService;
import software.amazon.awscdk.services.ecs.FargateTaskDefinition;
import software.amazon.awscdk.services.ecs.LogDriver;
import software.amazon.awscdk.services.ecs.PortMapping;
import software.amazon.awscdk.services.logs.LogGroup;
import software.constructs.Construct;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskDefStack extends Stack {
    public TaskDefStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public TaskDefStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        //进行运行环境定义

        //定义VPC
        Vpc vpc = Vpc.Builder.create(this, id + "MXVPC")
                .maxAzs(2)
                .vpcName("MXVPC")
                .cidr("10.0.0.0/16")
                .build();

        SubnetSelection subnetSelection=SubnetSelection.builder().subnets(vpc.getPublicSubnets()).build();

        //定义安全策略，放行80端口
        SecurityGroup securityGroup=SecurityGroup.Builder.create(this,id+"MXSECGROUP")
                .securityGroupName("MXSECGROUP")
                .vpc(vpc)
                .allowAllOutbound(true)
                .build();

        securityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(80),"ALLOWHTTP80");

        //定义cluster
        Cluster cluster = Cluster.Builder.create(this, id + "MXCLUSTER")
                .clusterName("MXCLUSTER")
                .vpc(vpc)
                .build();


        //定义日志
        AwsLogDriverProps awsLogDriverProps=AwsLogDriverProps.builder().logGroup(LogGroup.fromLogGroupName(this,id+"MXLOGDRIVERGROUP","MXLOG")).streamPrefix("MX").build();

        //定义容器运行任务
        FargateTaskDefinition fargateTaskDefinition = FargateTaskDefinition.Builder.create(this, id + "MXFARGATETASKDEF")
                .cpu(256)
                .memoryLimitMiB(512)
                .build();


        ContainerDefinition containerDefinition = ContainerDefinition.Builder.create(this, id + "MXCONTAINERDEF")
                .image(
                        ContainerImage.fromEcrRepository(
                                Repository.fromRepositoryName(this,id+"MXIMAGEREPO","mximagerepo"),
                                "695a5133ec8b6a034352c4d187742f92f2845d9b"
                        )
                )
                .taskDefinition(fargateTaskDefinition)
                .portMappings(Stream.of((PortMapping) () -> 80)
                        .collect(Collectors.toList()))
                .logging(LogDriver.awsLogs(awsLogDriverProps))
                .build();

        //定义服务
        FargateService fargateService = FargateService.Builder.create(this, id + "MXFARGATESERVICE")
                .serviceName("MXFARGATESERVICE")
                .cluster(cluster)
                .taskDefinition(fargateTaskDefinition)
                .platformVersion(FargatePlatformVersion.LATEST)
                .vpcSubnets(subnetSelection)
                .securityGroups(Stream.of(securityGroup).collect(Collectors.toList()))
                .assignPublicIp(true)
                .build();

    }
}
