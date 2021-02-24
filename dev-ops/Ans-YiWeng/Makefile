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

# CI for Q1
ci-Q11: clean stage deps run-Q1

run-Q1:
	cd ./stage/Q1/ && sh ./scripts/run.sh

# for Q2
ci-Q2: clean deps lint package

package: stage
