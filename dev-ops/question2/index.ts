import ec2 = require('@aws-cdk/aws-ec2');
import ecs = require('@aws-cdk/aws-ecs');
import waf = require('@aws-cdk/aws-waf');
import ecs_patterns = require('@aws-cdk/aws-ecs-patterns');
import cdk = require('@aws-cdk/core');


class Question2FargateDemo extends cdk.Stack {
  constructor(scope: cdk.App, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    // Create Private VPC and Fargate Cluster
    const vpc = new ec2.Vpc(this, 'MyVpc', {
	    	subnetConfiguration: [{
		        cidrMask: 26,
		        name: 'privatedSubnet',
		        subnetType: SubnetType.PRIVATE,
		    }]
    	});
    const cluster = new ecs.Cluster(this, 'Cluster', { vpc });

    // Instantiate Fargate Service with just cluster and image
    new ecs_patterns.ApplicationLoadBalancedFargateService(this, "FargateService", {
      cluster,
      taskImageOptions: {
        image: ecs.ContainerImage.fromRegistry("amazon/amazon-ecs-sample"),
      },
    });
  }
}

const app = new cdk.App();

new Question2FargateDemo(app, 'Question2Fargate');

app.synth();