# Basic build commands
clean:
	rm -rf stage

stage:
	mkdir -p stage/
	cp -R Q1 stage/
	cp -R Q2 stage/
	cp ../DevOps_interview_data_set.gz stage/Q1/

deps:
	pip3 install -r requirements.txt

#########################################################################################
# Q1                                                                                    #
# CI for Q1                                                                             #
#########################################################################################
ci-q1: clean stage deps run-q1

run-q1:
	cd ./stage/Q1/ && sh ./scripts/run.sh

#########################################################################################
# Q2                                                                                    #
# App helloworldwebapp variables for Q2                                                 #
#########################################################################################
app = helloworldwebapp
region="ap-southeast-2"
repo_id="xxxxxx.dkr.ecr.REGION.amazonaws.com"
branch=$(shell git rev-parse --abbrev-ref HEAD)
# version: version of machine images to be created
q2_version ?= "1.0.0"

#########################################################################################
# Q2                                                                                    #
# App helloworldwebapp functions for Q2                                                 #
#########################################################################################
# ci for Q2
ci-q2: deps clean stage package integration-test publish deploy-ecs-service

# Create/update infrastructure env
create-infra-resources: clean stage
	cd ./stage/Q2/aws-fargate/ && sceptre launch-env resources

create-infra-service: clean stage
	cd ./stage/Q2/aws-fargate/ sceptre launch-env helloworld

# delete infrastructure env
delete-infra-resources: clean stage
	cd ./stage/Q2/aws-fargate/ && sceptre delete-env resources

delete-infra-service: clean stage
	cd ./stage/Q2/aws-fargate/ && sceptre delete-env helloworld

# build docker image with target code version
package: clean stage
	cd ./stage/Q2/ && docker build -t $(app):$(q2_version) --build-arg app=$(app)  --build-arg version=$(q2_version) .

# publish image to ecr
publish: clean stage
	sh ./stage/Q2/scripts/publish_image_ecr.sh $(app) $(q2_version) $(repo_id) $(region)

integration-test:
	sh ./stage/Q2/scripts/integration_test.sh $(app) $(q2_version)

# build local web app
deploy-local:
	sh ./stage/Q2/scripts/deploy_local.sh $(app) $(q2_version) $(container_name)

delete-local:
	docker rm -f $(container_name)

# delete all ecr images
delete-images: clean stage
	sh ./stage/Q2/scripts/delete_ecr_images.sh $(app) $(region)

# deploy direct deployment to ecs fargate env
deploy-ecs-service: clean stage
	cd ./stage/Q2 && ls deployments && sh ./scripts/deploy_ecs.sh $(app) $(q2_version) $(repo_id) $(region)

.PHONY: clean stage deps ci-q1 run-q1 ci-q2 package publish integration-test create-infra-resources delete-infra-resources create-infra-service delete-infra-service deploy-ecs-service deploy-local delete-local