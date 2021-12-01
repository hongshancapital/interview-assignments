terraform {
  required_version = ">= 0.14"

  backend "local" {
    path = "../terraform.tfstate"
  }
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 3.66.0"
    }
  }
}

provider "aws" {
  region = var.my_region
}