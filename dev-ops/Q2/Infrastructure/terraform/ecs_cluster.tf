resource "aws_cloudwatch_log_group" "helloword" {
  name = var.project_name
}

resource "aws_ecs_cluster" "helloword" {
  name               = var.project_name
  capacity_providers = ["FARGATE"]

  configuration {
    execute_command_configuration {
      logging = "OVERRIDE"

      log_configuration {
        cloud_watch_encryption_enabled = true
        cloud_watch_log_group_name     = aws_cloudwatch_log_group.helloword.name
      }
    }
  }
}