
variable "my_region" {
  default = "us-east-1"
}

variable "project_name" {
  default = "helloworld"
}

variable "vpc_cidr_block" {
  default = "10.0.0.0/16"
}

variable "subnet_private_cidr_block" {
  default = "10.0.1.0/24"
}

variable "subnet_public_cidr_block" {
  default = "10.0.2.0/24"
}

variable "my_certificate_arn" {
  default = "arn:aws:iam::187416307283:server-certificate/test_cert_rab3wuqwgja25ct3n4jdj2tzu4"
}

variable "ecs_image" {
  default = "ECS_IMAGE"
}

data "aws_iam_role" "iam_hello" {
  name = "iam_hello"
}

data "aws_iam_policy" "iam_policy_hello" {
    name = "iam_policy_hello"
}
