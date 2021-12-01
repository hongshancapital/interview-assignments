output "cloudwatch_metric_alarm_arn" {
  description = "The ARN of the Cloudwatch metric alarm."
  value       = element(concat(aws_cloudwatch_metric_alarm.this.*.arn, [""]), 0)
}

output "cloudwatch_metric_alarm_id" {
  description = "The ID of the Cloudwatch metric alarm."
  value       = element(concat(aws_cloudwatch_metric_alarm.this.*.id, [""]), 0)
}
