module "aws_sns_topic" {
  source = "fixtures/aws_sns_topic"
}

module "log_group" {
  source = "modules/log-group"

  name = var.project_name
  name_prefix = "hello-"
}

locals {
  metric_transformation_name      = "ErrorCount"
  metric_transformation_namespace = "MyAppNamespace"
}

module "log_metric_filter" {
  source = "modules/log-metric-filter"

  log_group_name = module.log_group.cloudwatch_log_group_name

  name    = var.project_name
  pattern = "ERROR"

  metric_transformation_namespace = local.metric_transformation_namespace
  metric_transformation_name      = local.metric_transformation_name
}

module "alarm" {
  source = "modules/metric-alarm"

  alarm_name          = var.project_name
  alarm_description   = "Log errors are too high"
  comparison_operator = "GreaterThanOrEqualToThreshold"
  evaluation_periods  = 1
  threshold           = 10
  period              = 60
  unit                = "Count"

  namespace   = local.metric_transformation_namespace
  metric_name = local.metric_transformation_name
  statistic   = "Sum"

  alarm_actions = [module.aws_sns_topic.sns_topic_arn]
}
