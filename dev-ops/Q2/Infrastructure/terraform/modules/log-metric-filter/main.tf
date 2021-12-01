resource "aws_cloudwatch_log_metric_filter" "this" {
  count = var.create_cloudwatch_log_metric_filter ? 1 : 0

  name           = var.name
  pattern        = var.pattern
  log_group_name = var.log_group_name

  metric_transformation {
    name          = var.metric_transformation_name
    namespace     = var.metric_transformation_namespace
    value         = var.metric_transformation_value
    default_value = var.metric_transformation_default_value
  }
}
