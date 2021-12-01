
resource "aws_ecs_service" "helloword" {
  name            = var.project_name
  cluster         = aws_ecs_cluster.helloword.id
  task_definition = aws_ecs_task_definition.service.arn
  desired_count   = 3
  iam_role        = data.aws_iam_role.iam_hello.arn
  depends_on      = [data.aws_iam_policy.iam_policy_hello]

  ordered_placement_strategy {
    type  = "binpack"
    field = "cpu"
  }

  load_balancer {
    target_group_arn = aws_lb_target_group.helloword.arn
    container_name   = var.project_name
    container_port   = 9092
  }
}