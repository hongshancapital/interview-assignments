# version: version of machine images to be created
q2_version ?= 1.0.0
docker_user_name = wingasun
region="ap-southeast-2"
repo_id="572390290449.dkr.ecr.ap-southeast-2.amazonaws.com"
# App name for Q2
app = helloworldwebapp


# Basic build commands
clean:
	rm -rf stage

stage:
	mkdir -p stage/
	cp -R Q1 stage/
	cp -R Q2 stage/
	cp ../DevOps_interview_data_set.gz stage/Q1/

deps:
	pip install -r requirements.txt

# Q1
# CI for Q1
ci-q1: clean stage deps run-q1

run-q1:
	cd ./stage/Q1/ && sh ./scripts/run.sh

# Q2
# for Q2
ci-q2: clean stage deps

package: clean stage
	cd ./stage/Q2/ && docker build -t $(app):$(q2_version) --build-arg app=$(app)  --build-arg version=$(q2_version) .

publish: clean stage
	sh ./stage/Q2/scripts/publish_image_ecr.sh $(app) $(q2_version) $(repo_id) $(region)

integration-test:
	sh ./stage/Q2/scripts/integration_test.sh $(app) $(q2_version)
