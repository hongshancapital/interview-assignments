
# CI for Q1
ci1: clean stage runQ1

stage:
	mkdir -p stage/
	cp -R Q1 stage/
	cp -R Q2 stage/
	cp ../DevOps_interview_data_set.gz stage/Q1/

clean:
	rm -rf stage

runQ1:
	cd ./stage/Q1/ && sh ./scripts/run.sh



# for Q2

ci2: clean deps lint package

package: stage
