//  pipe 脚本 完成 Simplest-Spring-Boot-Hello-World 项目 git拉取->编译->镜像->上传->发版动作
// gitlab需要配置密钥凭证
pipeline {
    agent any
    environment { 
        git_url = "https://github.com/goxr3plus/Simplest-Spring-Boot-Hello-World.git"
        git_branch_name = "master"
        gitlab_auth_id = "gitlab"
        build_cmd = "mvn clean package"
        image_prefix = "foo.com/dev-image"
        image_name = ""
        version = ""
        sonar_props = "-Dsonar.projectKey=${JOB_NAME} -Dsonar.projectName=${JOB_NAME} -Dsonar.language=java -Dsonar.sourceEndcoding=UTF-8 -Dsonar.sources=src -Dsonar.java.binaries=target/test-classes"
        k8s_env = "dev"
        k8s_user = "root"
        k8s_host_ip = "10.10.0.1"
        tip = "deploy"
    }
    stages {
        stage('前置检查') {
            steps {
                // 删除历史镜像
                sh "/var/jenkins_home/script/ci-rm-image.sh"
            }
        }
        stage('拉取代码') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: "${git_branch_name}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: "${gitlab_auth_id}", url: "${git_url}"]]])
            }
        }

        stage('编译代码') {
            tools {
               jdk 'jdk11'
               maven 'maven'
            }
            steps {
                sh "${build_cmd}"
            }
        }
        stage('构建镜像') {
            tools {
               docker '19.03.14'
            }
            steps {
                sh "${build_cmd}"
            }
        }        
        stage('镜像上传') {
            steps {
                script {
                    image_name = sh(script: "/var/jenkins_home/script/ci-push-image.sh ${git_branch_name}", returnStdout: true).trim()
                }
            }
        }
        stage('镜像发版') {
            steps {
                 script {
                    sh """ssh ${k8s_user}@${k8s_host_ip} "/opt/jenkins-exec/script/cd-update-image.sh -e \"${k8s_env}\" -m \"${JOB_NAME}\" -v \"${image_name}\"" """
               }  
            }
        }
    }
    
    // 后置处理
    post('后置处理') {
        always {
            cleanWs()
        }
        success {
            sh """ 
                # 发布成功通知发送信息到微信群
                curl 'https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=xxxxxx' \
                -H 'Content-Type: application/json;charset: utf-8' \
                -d '
                {
                        "msgtype": "markdown",
                        "markdown": {
                                 "content": "> '${image_name}' for <font color='info'>'${k8s_env}'</font> '${tip}' succeed"
                        }
                }'
            """
        }
        failure {
            // 失败触发日志文件投递到发起人的email邮箱中
            script{
		        emailext subject: '$DEFAULT_SUBJECT',
			        body: '$DEFAULT_CONTENT',
			        to:'$email',
			        attachLog: true,
			        mimeType: 'text/html'
	            }
        }   
    }
}