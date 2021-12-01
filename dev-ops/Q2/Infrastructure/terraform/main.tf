resource "aws_vpc" "main" {
  cidr_block       = var.vpc_cidr_block
  instance_tenancy = "default"

  tags = {
    Name = "main"
  }
}

resource "aws_ecr_repository" "main" {
  name                 = var.project_name
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

resource "aws_subnet" "main_private" {
  vpc_id     = aws_vpc.main.id
  cidr_block = var.subnet_private_cidr_block

  tags = {
    Name = "Private"
  }
}

resource "aws_subnet" "main_public" {
  vpc_id     = aws_vpc.main.id
  cidr_block = var.subnet_public_cidr_block

  tags = {
    Name = "Public"
  }
}

resource "aws_security_group" "allow_tls" {
  name        = "allow_tls"
  description = "Allow TLS inbound traffic"
  vpc_id      = aws_vpc.main.id

  ingress {
    description      = "TLS from VPC"
    from_port        = 443
    to_port          = 443
    protocol         = "tcp"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name = "allow_tls"
  }
}

resource "aws_security_group" "default" {
  name        = var.project_name
  description = "Allow all interal traffic"
  vpc_id      = aws_vpc.main.id

  ingress {
    description      = "All from VPC"
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = [aws_vpc.main.cidr_block]
    ipv6_cidr_blocks = []
  }

  egress {
    from_port        = 0
    to_port          = 0
    protocol         = "-1"
    cidr_blocks      = ["0.0.0.0/0"]
    ipv6_cidr_blocks = ["::/0"]
  }

  tags = {
    Name = "allow_tls"
  }
}

resource "aws_lb" "helloworld" {
  name               = var.project_name
  internal           = false
  load_balancer_type = "application"
  security_groups    = [aws_security_group.allow_tls.id]
  subnets            = [aws_subnet.main_public.id]

  enable_deletion_protection = true

  tags = {
    Environment = "testing"
  }
}

resource "aws_lb_target_group" "helloworld" {
  name     = var.project_name
  port     = 9092
  protocol = "HTTP"
  vpc_id   = aws_vpc.main.id
}

resource "aws_lb_listener" "helloworld_front_end" {
  load_balancer_arn = aws_lb.helloworld.arn
  port              = "443"
  protocol          = "HTTPS"
  ssl_policy        = "ELBSecurityPolicy-2016-08"
  certificate_arn   = var.my_certificate_arn

  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.helloworld.arn
  }
}
