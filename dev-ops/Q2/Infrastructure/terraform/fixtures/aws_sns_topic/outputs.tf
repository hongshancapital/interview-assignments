output "sns_topic_arn" {
  description = "ARN"
  value       = aws_sns_topic.this.arn
}

output "sns_topic_name" {
  description = "Name"
  value       = aws_sns_topic.this.name
}
