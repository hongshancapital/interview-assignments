package com.mx.devops.init;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.cloudwatch.Alarm;
import software.amazon.awscdk.services.cloudwatch.ComparisonOperator;
import software.amazon.awscdk.services.cloudwatch.Metric;
import software.amazon.awscdk.services.cloudwatch.MetricOptions;
import software.amazon.awscdk.services.cloudwatch.actions.SnsAction;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.logs.FilterPattern;
import software.amazon.awscdk.services.logs.LogGroup;
import software.amazon.awscdk.services.logs.MetricFilter;
import software.amazon.awscdk.services.sns.Topic;
import software.amazon.awscdk.services.sns.subscriptions.EmailSubscription;
import software.constructs.Construct;

public class InitStack extends Stack {
    public InitStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public InitStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        //进行初始资源定义，仅需执行一次

        //定义镜像库
        Repository repository = Repository.Builder.create(this, id + "MXIMAGEREPO")
                .repositoryName("mximagerepo")
                .build();

        //定义日志
        LogGroup logGroup = LogGroup.Builder.create(this, id + "MXLOG").logGroupName("MXLOG").build();

        //定义邮件发送
        Topic topic = Topic.Builder.create(this, id + "MXSNSTOPIC").topicName("MXSNSTOPIC").build();

        EmailSubscription emailSubscription = EmailSubscription.Builder.create("mx1985abc@163.com").build();

        topic.addSubscription(emailSubscription);

        SnsAction snsAction = new SnsAction(topic);

        //定义监控指标
        MetricFilter metricFilter = MetricFilter.Builder.create(this, id + "MXLOGFILTER")
                .metricName("MXLOGFILTER")
                .metricNamespace("MXMERTICSPACE")
                .metricValue("1")
                .defaultValue(0)
                .filterPattern(FilterPattern.anyTerm("ERROR"))
                .logGroup(logGroup)
                .build();

        Metric metric = metricFilter.metric(MetricOptions.builder()
                .period(Duration.seconds(10))
                .statistic("Sum")
                .build());

        //定义告警
        Alarm alarm = Alarm.Builder.create(this, id + "MXLOGALARM")
                .alarmName("MXLOGALARM")
                .metric(metric)
                .evaluationPeriods(1)
                .comparisonOperator(ComparisonOperator.GREATER_THAN_OR_EQUAL_TO_THRESHOLD)
                .threshold(1)
                .actionsEnabled(true)
                .build();

        alarm.addAlarmAction(snsAction);

    }
}
