resource "random_pet" "this" {
  length = 2
}

resource "aws_sns_topic" "this" {
  name = "fixtures-${random_pet.this.id}"
}
