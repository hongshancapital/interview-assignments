# DevOps

# 1. Q1

通过postman的http接口调用， 接口没法上传， 如下图所示：

<img src="/Users/lingjing/PycharmProjects/interview-assignments/dev-ops/images/img.png" alt="img" style="zoom:67%;" />

python代码如下：

```python
#!/usr/bin/env python
# --coding:utf-8 --
import json
import re

import requests
import ssl
try:
    _create_unverified_https_context=ssl._create_unverified_context
except AttributeError:
    pass
else:
    ssl._create_default_https_context=_create_unverified_https_context


def get_device_name(device_name):
    # 进行相应的正则匹配和配对
    if device_name == 'BBAOMACBOOKAIR2':
        return device_name
    else:
        return None


def get_process_id(line):
    start = 0
    j = 0
    for i, item in enumerate(line):
        if item == " ":
            if j == 3:
                start = i
            j = j + 1
    end = re.search(": ", line).start()
    s = line[start: end]
    partern = re.compile("\d+")
    res = re.findall(partern, s)
    if len(res) > 0: return res[len(res) - 1]
    return res


def get_process_name(line):
    start = 0
    j = 0
    for i, item in enumerate(line):
        if item == " ":
            if j == 3:
                start = i
            j = j + 1
    end = re.search(": ", line).start()
    s = line[start: end].split(" ")[-1]
    m = ''.join(re.split("\d+", s)).replace("(", "").replace(")", "").replace("[", "").replace("]", "").replace(":", "")
    if m.endswith("."):
        return m[:-1]
    return m

def get_description(line):

    start = re.search(": ", line).start()
    return line[start + 1:]


def get_hour(time):
    hour = time.split(":")[0]
    return hour


def deal_file(file_name):
    map1 = {}
    with open(file_name, "r") as f:
        for line in f.readlines():
            device_name = get_device_name(line.split(" ")[3])
            if device_name is None: continue
            process_id = get_process_id(line)
            if process_id is None: continue
            process_name = get_process_name(line)
            if process_name is None: continue
            description = get_description(line)
            if description is None: continue
            hour = get_hour(line.split(" ")[2])
            if hour is None: continue

            key = hash(hour + "    " + device_name + "   " + process_id + "    " + process_name + "    " + description)
            if map1.get(key) is None:
                map2 = {}
                map2["hour"] = hour
                map2["device_name"] = device_name
                map2["process_id"] = process_id
                map2["process_name"] = process_name
                map2["description"] = description
                map2["number_of_occurrence"] = 1
                map1[key] = map2
            else:
                map1.get(key)["number_of_occurrence"] = map1.get(key).get("number_of_occurrence") + 1
    url = "https://foo.com/bar"
    l1 = list()
    for value in map1.values():
        print(json.dumps(value))
        # r = requests.post(url=url, data=value, verify=False)
        # print(r)





if __name__ == '__main__':
    deal_file("./DevOps_interview_data_set");
```



执行结果如下图所示：

![img_1](/Users/lingjing/PycharmProjects/interview-assignments/dev-ops/images/img_1.png)



# 2. Q2

## 1. devops数据流向图

![devops构建流程](/Users/lingjing/PycharmProjects/interview-assignments/dev-ops/images/devops构建流程.png)

## 2. devops架构设计图

![DevOps架构设计](/Users/lingjing/PycharmProjects/interview-assignments/dev-ops/images/DevOps架构设计.png)

## 3. Dockerfile

```dockerfile
FROM harbor.xxx.com/devops-base-registry/centos7:v1.0.0

ARG USER_OPTS=""
ARG APP_NAME=k8s-demo

ARG SERVER_PORT=8080

ARG JVM_OPTS="-Xmx1g -Xms1g -Xss256k -XX:MaxDirectMemorySize=1G -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:G1ReservePercent=25 \
  -XX:InitiatingHeapOccupancyPercent=40 -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M \
  -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC -XX:-OmitStackTraceInFastThrow -XX:+PrintCommandLineFlags -Djava.awt.headless=true \
  -Djava.net.preferIPv4Stack=true -Djava.util.Arrays.useLegacyMergeSort=true -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom"

ENV SERVER_PORT=${SERVER_PORT} \
  APP_FOLDER=/data/apps \
  JAR_FILE=./*.jar \
  APP_NAME=${APP_NAME} \
  JAVA_OPTS=${JAVA_OPTS}


EXPOSE ${SERVER_PORT}

RUN mkdir -p ${APP_FOLDER}/${APP_NAME} ${APP_FOLDER}/${APP_NAME}/temp ${APP_FOLDER}/${APP_NAME}/apollo ${APP_FOLDER}/${APP_NAME}/app ${APP_FOLDER}/${APP_NAME}/logs; \
  touch ${APP_FOLDER}/${APP_NAME}/logs/${APP_NAME}.log

ENV APOLLO_OPTS="-Dapollo.private.key=${APP_FOLDER}/${APP_NAME}/apollo/apollo-private-key" \
  APP_OPTS="-Dlogging.path=${APP_FOLDER}/${APP_NAME}/logs -Djava.io.tmpdir=${APP_FOLDER}/${APP_NAME}/temp -Xloggc:${APP_FOLDER}/${APP_NAME}/logs/gc.log -XX:HeapDumpPath=${APP_FOLDER}/${APP_NAME}/logs/java.hprof"

ENV JAVA_OPTS="-server ${JVM_OPTS} ${APP_OPTS} ${APOLLO_OPTS} ${USER_OPTS}"

WORKDIR ${APP_FOLDER}/${APP_NAME}
COPY ${JAR_FILE} ${APP_FOLDER}/${APP_NAME}/app/${APP_NAME}.jar

COPY ./apollo-private-key ${APP_FOLDER}/${APP_NAME}/apollo/apollo-private-key

ENTRYPOINT [ "sh", "-c", "java ${JAVA_OPTS} -jar ${APP_FOLDER}/${APP_NAME}/app/${APP_NAME}.jar >> ${APP_FOLDER}/${APP_NAME}/logs/${APP_NAME}.log" ]





```

## 4. JenkinsFile

```groovy
currentBuild.description = pipeline_name + ":" + branch
node('maven') {
    env.MAVEN_HOME = "${tool 'MAVEN_3'}"
    env.PATH="${env.MAVEN_HOME}/bin:${env.PATH}"

    stage('Preparation') {
        cleanWs deleteDirs: true
        key = 'devops_bot'
        checkout([$class: 'GitSCM', branches: [[name: branch]],
          userRemoteConfigs: [[url: repo, credentialsId: key]]])
    }

    stage('Build') {
        sh 'if [ -f "pom.xml" ]; then mvn $args -B -Dmaven.test.skip=true clean package -U; fi'
        sh 'if [ -f "build.gradle" ]; then gradle bootJar; fi'
    }

    stage('Test') {
        if (branch != 'master') {
            timeout(15){
            sh 'mvn org.jacoco:jacoco-maven-plugin:prepare-agent test -Dmaven.test.failure.ignore=true -Dmaven.test.skip=false'
            //junit '**/target/surefire-reports/*.xml' surefire:test
            }
        }
    }

    stage('Sonar') {
        if (branch != 'master') {
            sh 'mvn -Dsonar.projectKey=$pipeline_name -Dsonar.projectName=${alm_id}-${pipeline_name} -Dsonar.links.homepage=$repo -Dsonar.links.scm=$repo -Dsonar.scm.provider=git -Dsonar.analysis.buildNumber=${BUILD_ID} sonar:sonar'
        }
    }
  
    stage('Results') {
        // addGitLabMRComment comment: 'Jenkins CI passed'
        // gitlabCommitStatus(name: 'jenkins-ci', connection: gitLabConnection('git.longhu.net')) {}
    }

    stage('Link') {
        sh 'cp `find . -size +15M -name "*.jar"` .'
        sh 'for i in *jar; do ossutil cp $i oss://xxx/record/${BUILD_ID}/${pipeline_name}/; done'
        archiveArtifacts artifacts: '*.jar', fingerprint: true, onlyIfSuccessful: true
        cleanWs deleteDirs: true
    }
}

```

## 5. jenkins的接口调用

jenkins的接口调用

```java
package com.longfor.minevar.ci.repo.client.jenkins;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.longfor.gaia.gfs.web.feign.LFFeignClient;
import com.longfor.gaia.gfs.web.feign.config.LFFeignConfiguration;
import com.longfor.minevar.ci.config.FeignQueryMapEncoderConfiguration;
import com.longfor.minevar.ci.repo.client.jenkins.dto.JenkinsBuildStageInfoDTO;
import com.longfor.minevar.ci.repo.client.jenkins.dto.JenkinsJobBuildDetailResp;
import com.longfor.minevar.ci.repo.client.jenkins.dto.JenkinsJobDetailResp;
import com.longfor.minevar.ci.repo.client.jenkins.dto.JenkinsJobTestResultActionDTO;
import com.longfor.minevar.ci.repo.client.jenkins.dto.JenkinsQueueItemResp;

import feign.QueryMap;



@LFFeignClient(value = "ci-jenkins-job", group = "jenkins", decode404 = true, configuration = {
		LFFeignConfiguration.class, FeignQueryMapEncoderConfiguration.class })
public interface JenkinsJobClient {

	@GetMapping(value = "/queue/item/{itemId}/api/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<JenkinsQueueItemResp> getQueueItemByItemId(@PathVariable("itemId") int itemId,
			@RequestHeader("Authorization") String authorization);

	

	/**
	 * Description: 获取Jenkins Build Stage状态
	 *
	 * @param jobName
	 * @param jenkinsBuildId
	 * @return 
	 */
	@GetMapping(value = "/job/{jobName}/{jenkinsBuildId}/wfapi/describe", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<JenkinsBuildStageInfoDTO> getJenkinsJobBuildDetailStage(@PathVariable("jobName") String jobName,
			@PathVariable("jenkinsBuildId") Integer jenkinsBuildId,
			@RequestHeader("Authorization") String authorization);

	/**
	 * @param jobName
	 * @param jenkinsBuildId
	 * @param authorization
	 * @return
	 */
	@GetMapping(value = "/job/{jobName}/{jenkinsBuildId}/api/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<JenkinsJobTestResultActionDTO> getJenkinsJobTestResultAction(@PathVariable("jobName") String jobName,
			@PathVariable("jenkinsBuildId") Integer jenkinsBuildId,
			@RequestHeader("Authorization") String authorization);

	/**
	 * Description: 获取Jenkins Job某次构建全量日志输出
	 *
	 * @param jobName
	 * @param jenkinsBuildId
	 * @return 
	 */
	@GetMapping(value = "/job/{jobName}/{jenkinsBuildId}/consoleText", produces = MediaType.TEXT_PLAIN_VALUE)
	ResponseEntity<String> getJenkinsJobBuildConsoleText(@PathVariable("jobName") String jobName,
			@PathVariable("jenkinsBuildId") Integer jenkinsBuildId,
			@RequestHeader("Authorization") String authorization);

	/**
	 * Description: 获取Jenkins Job某次构建，根据start偏移量返回增量日志输出
	 *
	 * @param jobName
	 * @param jenkinsBuildId
	 * @param start
	 * @return 
	 */
	@GetMapping(value = "/job/{jobName}/{jenkinsBuildId}/logText/progressiveText", produces = MediaType.TEXT_PLAIN_VALUE)
	ResponseEntity<String> getJenkinsJobBuildProgressiveText(@PathVariable("jobName") String jobName,
			@PathVariable("jenkinsBuildId") Integer jenkinsBuildId,
			@RequestParam(value = "start", defaultValue = "0", required = false) Integer start,
			@RequestHeader("Authorization") String authorization);

	@GetMapping(value = "/job/{jobName}/api/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<JenkinsJobDetailResp> getJenkinsJobDetail(@PathVariable("jobName") String jobName,
			@RequestHeader("Authorization") String authorization);

	@GetMapping(value = "/job/{jobName}/{buildNumber}/api/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity<JenkinsJobBuildDetailResp> getJenkinsJobBuildDetail(@PathVariable("jobName") String jobName,
			@PathVariable("buildNumber") Integer buildNumber, @RequestHeader("Authorization") String authorization);

	@PostMapping(value = "/job/{jobName}/{buildNumber}/stop", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity stopJenkinsBuildJob(

			@PathVariable("jobName") String jobName,

			@PathVariable("buildNumber") Integer buildNumber,

			@RequestHeader("Authorization") String authorization);

	/**
	 * @desc: 通用方法, 触发CI-Jenkins任务feign接口申明
	 * @date: 2021/7/26:下午2:27
	 * @param jobName
	 * @param params
	 * @param authorization
	 * @return: org.springframework.http.ResponseEntity
	 */
	@PostMapping(value = "/job/{jobName}/buildWithParameters", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	ResponseEntity buildWithParametersCommon(@PathVariable("jobName") String jobName,
			@QueryMap Map<String, String> params, @RequestHeader("Authorization") String authorization);

    /**
     * @desc: 删除jenkins本地打包记录
     * @date: 2021/11/22:上午10:20
     * @param jobName
     * @param jBuildId
     * @param authorization
     * @return: org.springframework.http.ResponseEntity
     */
    @PostMapping(value = "/job/{jobName}/{jBuildId}/doDelete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity doDeleteJenkinsBuildRecord(@PathVariable("jobName") String jobName,@PathVariable("jBuildId") Integer jBuildId, @RequestHeader("Authorization") String authorization);
}

```

JenkinsJobRepoImpl.java

```java
@Repository
@Slf4j
public class JenkinsJobRepoImpl implements JenkinsJobRepo {

  @Override
	public Optional<String> buildJobWithParameters(String jobName, String repo, String pipeline, String branch,
			String almId, String args) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("repo", repo);
			map.put("pipeline_name", pipeline);
			map.put("branch", branch);
			map.put("alm_id", almId);
			map.put("args", args);
			ResponseEntity response = jenkinsJobClient.buildWithParametersCommon(jobName, map,
					ciJenkinsProperties.getUserToken());
			String location = response.getHeaders().get("Location").get(0);
			log.info("Trigger jenkins job ok, pipeline={}, jobName={}, repo={}, branch={}, almId={}, args={}, res={}",
					pipeline, jobName, repo, branch, almId, args, location);
			return Optional.of(location);
		} catch (FeignException e) {
			log.error("Trigger jenkins job feign error, pipeline={}, status={},  msg={}", pipeline, e.status(),
					e.getMessage());
		} catch (Exception e) {
			log.error("Trigger jenkins job fail, pipeline={}, msg={}", pipeline, e.getMessage());
		}
		return Optional.empty();
	}

}
```

## 6. CD的JenkinsFile

```groovy
import groovy.json.JsonSlurperClassic


node('prod') {
    def inventoryFile
    def params

    stage('Preparation') { // for display purposes
        timeout(2) {
            cleanWs deleteDirs: true
            params = generateParameters(json)
            currentBuild.description = params.description
            inventoryFile = tempInventoryFile()

            //windows
            if (params.extraVars.siberia_inventory_prefix?.trim()) {
                if (params.extraVars.windows_domain?.trim() == 'WUCAPITAL') {//windows domain with WUCAPITAL
                    print('WUCAPITAL')
                    withCredentials([file(credentialsId: params.extraVars.siberia_inventory_prefix + "-wc-prod", variable: 'inventoryPrefix')]) {
                        writeFile(file: inventoryFile, text: readFile(inventoryPrefix) + '\n' + params.hosts.join('\n'))
                    }
                } else {//windows domain with LONGHU
                    print('LONGHU')
                    withCredentials([file(credentialsId: params.extraVars.siberia_inventory_prefix + "-prod", variable: 'inventoryPrefix')]) {
                        writeFile(file: inventoryFile, text: readFile(inventoryPrefix) + '\n' + params.hosts.join('\n'))
                    }
                }
            } else {//linux
                writeFile(file: inventoryFile, text: params.hosts.join('\n'))
            }

            if (params.gitUri.startsWith('ssh://')) {
                checkout([$class: 'GitSCM', branches: [[name: params.ref]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'devops_bot_ssh', url: params.gitUri]]])
            } else {
                checkout([$class: 'GitSCM', branches: [[name: params.ref]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'devops_bot', url: params.gitUri]]])
            }
        }
    }
    stage('Deploy') {
        timeout(20) {
            dir(params.path ? params.path : '') {
                ansiColor('xterm') {
                    ansiblePlaybook(
                         installation: 'ansible',
                         colorized: true,
                         inventory: inventoryFile,
                         credentialsId: 'devops-prod',
                         disableHostKeyChecking: true,
                         playbook: params.scriptName,
                         extraVars: params.extraVars,
                         tags: params.tags,
                         skippedTags: params.skipTags
                    )
                }
            }
        }
    }
}

def tempInventoryFile() {
    def timestamp = System.currentTimeMillis()
    return "/tmp/inventory.${JOB_NAME}.${timestamp}.host"
}

@NonCPS
def generateParameters(json) {
    def jsonSlurper = new JsonSlurperClassic()

    def params = jsonSlurper.parseText(json)
    println("Parameters:")
    println(params)
    return params
}

```

## 7. K8S交付使用helm进行交付

Deployment.yaml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ include "boot.fullname" . }}
  labels:
    {{- include "boot.labels" . | nindent 4 }}
spec:
{{- if not .Values.autoscaling.enabled }}
  replicas: {{ .Values.replicaCount }}
{{- end }}
  selector:
    matchLabels:
      {{- include "boot.selectorLabels" . | nindent 6 }}
  template:
    metadata:
    {{- with .Values.podAnnotations }}
      annotations:
        {{- toYaml . | nindent 8 }}
    {{- end }}
      labels:
        {{- include "boot.selectorLabels" . | nindent 8 }}
    spec:
      {{- with .Values.imagePullSecrets }}
      imagePullSecrets:
        {{- toYaml . | nindent 8 }}
      {{- end }}
      serviceAccountName: {{ include "boot.serviceAccountName" . }}
      securityContext:
        {{- toYaml .Values.podSecurityContext | nindent 8 }}
      containers:
        - name: {{ .Chart.Name }}
          securityContext:
            {{- toYaml .Values.securityContext | nindent 12 }}
          image: "{{ .Values.image.repository }}:{{ .Values.image.tag | default .Chart.AppVersion }}"
          imagePullPolicy: {{ .Values.image.pullPolicy }}
          env:
          - name: LOG_FOLDER
            value: /data/apps
          - name: JAVA_OPTS
            value: "-Dlogging.path=/data/apps -Djava.io.tmpdir=/data/apps -Xloggc:/data/apps/gc.log -XX:HeapDumpPath=/data/apps/java.hprof -Xss256k -XX:MaxDirectMemorySize=1G -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:G1ReservePercent=25 -XX:InitiatingHeapOccupancyPercent=40 -XX:+PrintGCDateStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M -XX:+HeapDumpOnOutOfMemoryError -XX:+DisableExplicitGC -XX:-OmitStackTraceInFastThrow -XX:+PrintCommandLineFlags -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Djava.util.Arrays.useLegacyMergeSort=true -Dfile.encoding=UTF-8 -Djava.security.egd=file:/dev/./urandom -Dapollo.meta=http://apolloconfig.longfor.sit -Dapollo.env=SIT -Dapollo.cluster=uat+e429c20f4da8d8b8d03352340a3a805e88d4e624 -Dserver.port=80 -Xmx128m -Xmx256m"
          workingDir: /data/apps
          command: ["bash", "/start.sh", "https://xxx/record/302541/minevar-alert/minevar-alert-edge.jar"]
          ports:
            - name: http
              containerPort: 80
              protocol: TCP
            - name: boot-manage
              containerPort: 9980
              protocol: TCP
          livenessProbe:
            httpGet:
              path: /
              port: http
          readinessProbe:
            httpGet:
              path: /
              port: http
          resources:
            {{- toYaml .Values.resources | nindent 12 }}
      {{- with .Values.nodeSelector }}
      nodeSelector:
        {{- toYaml . | nindent 8 }}
      {{- end }}
      {{- with .Values.affinity }}
      affinity:
        {{- toYaml . | nindent 8 }}
      {{- end }}
      {{- with .Values.tolerations }}
      tolerations:
        {{- toYaml . | nindent 8 }}
      {{- end }}

```

Values.yaml

```yaml
# Default values for boot.
# This is a YAML-formatted file.
# Declare variables to be passed into your templates.

replicaCount: 1

image:
  repository: xxx/dt-docker-public/boot
  pullPolicy: IfNotPresent
  # Overrides the image tag whose default is the chart appVersion.
  tag: 0.1

imagePullSecrets: []
nameOverride: ""
fullnameOverride: ""

serviceAccount:
  # Specifies whether a service account should be created
  create: false
  # Annotations to add to the service account
  annotations: {}
  # The name of the service account to use.
  # If not set and create is true, a name is generated using the fullname template
  name: ""

podAnnotations: {}

podSecurityContext: {}
  # fsGroup: 2000

securityContext: {}
  # capabilities:
  #   drop:
  #   - ALL
  # readOnlyRootFilesystem: true
  # runAsNonRoot: true
  # runAsUser: 1000

service:
  type: ClusterIP
  port: 80

ingress:
  enabled: false
  annotations: {}
    # kubernetes.io/ingress.class: nginx
    # kubernetes.io/tls-acme: "true"
  hosts:
    - host: chart-example.local
      paths: []
  tls: []
  #  - secretName: chart-example-tls
  #    hosts:
  #      - chart-example.local

resources: 
  # We usually recommend not to specify default resources and to leave this as a conscious
  # choice for the user. This also increases chances charts run on environments with little
  # resources, such as Minikube. If you do want to specify resources, uncomment the following
  # lines, adjust them as necessary, and remove the curly braces after 'resources:'.
  limits:
    cpu: 2
    memory: 512Mi
  requests:
    cpu: 200m
    memory: 256Mi

autoscaling:
  enabled: false
  minReplicas: 1
  maxReplicas: 100
  targetCPUUtilizationPercentage: 80
  # targetMemoryUtilizationPercentage: 80

nodeSelector: {}

tolerations: []

affinity: {}

```

## 8. 设计自己的日志监控

Filebeat : 日志手机客户端， 上传到kafka

ELK:  存储日志

使用kafka不同的groupId， 进行重新消费日志， 消费中如果遇到ERROR关键字， 通过和公司内部的人员权限系统和devops的流水线， 进行项目负责人的告警



<u>**注意：** 我们在实际日志监控的时候， 可能很多时候并不是有ERROR关键字进行监控， 这样的意义并不是很大， 通过kafka消费数据， 使用flink-job进行相关的日志分析统计， 可能报错的时间不同， 但是日志报错的内容是相同的， 日志去掉时间进行hashcode求值， 进行数量统计更加有意义</u>



AWS CloudWatch 的， 我觉得应该是有hook需要对应的url，如果没有， 肯定有获取日志文件的地址， 直接吐到kafka中， 后续的消费告警逻辑是一样的。





## 9. Infrastructure的代码

AWs SDK的maven配置

```maven
<dependencies>
  <dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>ec2</artifactId>
  </dependency>
  <dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>s3</artifactId>
  </dependency>
  <dependency>
    <groupId>software.amazon.awssdk</groupId>
    <artifactId>dynamodb</artifactId>
  </dependency>
</dependencies>
```

参考官方文档：https://docs.aws.amazon.com/zh_cn/sdk-for-java/latest/developer-guide/examples-ec2.html

```

public class Test {
    // 创建实例
    public static String createEC2Instance(Ec2Client ec2,String name, String amiId ) {

        RunInstancesRequest runRequest = RunInstancesRequest.builder()
            .imageId(amiId)
            .instanceType(InstanceType.T1_MICRO)
            .maxCount(1)
            .minCount(1)
            .build();

        RunInstancesResponse response = ec2.runInstances(runRequest);
        String instanceId = response.instances().get(0).instanceId();

        Tag tag = Tag.builder()
            .key("Name")
            .value(name)
            .build();

        CreateTagsRequest tagRequest = CreateTagsRequest.builder()
            .resources(instanceId)
            .tags(tag)
            .build();

        try {
            ec2.createTags(tagRequest);
            System.out.printf(
                "Successfully started EC2 Instance %s based on AMI %s",
                instanceId, amiId);

            return instanceId;

        } catch (Ec2Exception e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        return "";
    }

    // 启动实例
    public static void startInstance(Ec2Client ec2, String instanceId) {

        StartInstancesRequest request = StartInstancesRequest.builder()
            .instanceIds(instanceId)
            .build();

        ec2.startInstances(request);
        System.out.printf("Successfully started instance %s", instanceId);
    }

    // 停止实例
    public static void stopInstance(Ec2Client ec2, String instanceId) {

        StopInstancesRequest request = StopInstancesRequest.builder()
            .instanceIds(instanceId)
            .build();

        ec2.stopInstances(request);
        System.out.printf("Successfully stopped instance %s", instanceId);
    }
    
}
```

