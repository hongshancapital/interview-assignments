
resource "aws_ecs_task_definition" "service" {
  family = "service"
  container_definitions = jsonencode([
    {
      name      = var.project_name
      image     = var.ecs_image
      cpu       = 10
      memory    = 512
      essential = true
      portMappings = [
        {
          containerPort = 9092
          hostPort      = 9092
        }
      ]
    }
  ])

  volume {
    name      = "service-storage"
    host_path = "/ecs/service-storage"
  }

}