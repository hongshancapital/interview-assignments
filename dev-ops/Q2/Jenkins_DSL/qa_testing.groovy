pipelineJob("qa_testing") {
	description("qa testing")
	keepDependencies(false)
	parameters {
		stringParam("Docker_Image_Version", "v0.0.1", "docker image version")
	}
	definition {
		cpsScm {
			scm {
				git {
					remote {
						github("wyangsun/ECS-CICD", "https")
						credentials("credentials_id")
					}
					branch("*/features*")
				}
			}
			scriptPath("example/Jenkinsfile")
		}
	}
	disabled(false)
	configure {
		it / 'properties' / 'jenkins.model.BuildDiscarderProperty' {
			strategy {
				'daysToKeep'('-1')
				'numToKeep'('100')
				'artifactDaysToKeep'('-1')
				'artifactNumToKeep'('-1')
			}
		}
		it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
			'autoRebuild'('false')
			'rebuildDisabled'('false')
		}
	}
}