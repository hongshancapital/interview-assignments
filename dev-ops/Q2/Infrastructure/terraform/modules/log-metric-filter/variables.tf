variable "create_cloudwatch_log_metric_filter" {
  description = "Whether to create the Cloudwatch log metric filter"
  type        = bool
  default     = true
}

variable "name" {
  description = "A name for the metric filter."
  type        = string
}

variable "pattern" {
  description = "A valid CloudWatch Logs filter pattern for extracting metric data out of ingested log events."
  type        = string
}

variable "log_group_name" {
  description = "The name of the log group to associate the metric filter with"
  type        = string
}


variable "metric_transformation_name" {
  description = "The name of the CloudWatch metric to which the monitored log information should be published (e.g. ErrorCount)"
  type        = string
}

variable "metric_transformation_namespace" {
  description = "The destination namespace of the CloudWatch metric."
  type        = string
}

variable "metric_transformation_value" {
  description = "What to publish to the metric. For example, if you're counting the occurrences of a particular term like 'Error', the value will be '1' for each occurrence. If you're counting the bytes transferred the published value will be the value in the log event."
  type        = string
  default     = "1"
}

variable "metric_transformation_default_value" {
  description = "The value to emit when a filter pattern does not match a log event."
  type        = string
  default     = null
}
