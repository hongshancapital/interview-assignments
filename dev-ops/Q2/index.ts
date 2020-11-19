import ec2 = require('@aws-cdk/aws-ec2');
import ecs = require('@aws-cdk/aws-ecs');
import ecs_patterns = require('@aws-cdk/aws-ecs-patterns');
import cdk = require('@aws-cdk/core');
import waf = require('@aws-cdk/aws-waf');

class MyTestFargate extends cdk.Stack {
  constructor(scope: cdk.App, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    // Create VPC and Fargate Cluster
    const vpc = new ec2.Vpc(this, 'MyTestVpc', {
      maxAzs: 3,
      subnetConfiguration: [
        {
          name: 'PrivateService',
          subnetType: ec2.SubnetType.PRIVATE,
        }
      ]
    });
    const cluster = new ecs.Cluster(this, 'Cluster', { vpc });

    // Instantiate Fargate Service with just cluster and image
    new ecs_patterns.ApplicationLoadBalancedFargateService(this, "MyTestFargateService", {
      cluster,
      taskImageOptions: {
        image: ecs.ContainerImage.fromRegistry("amazon/amazon-ecs-sample"),
      },
      publicLoadBalancer: false,
    });
  }
}

const app = new cdk.App();

new MyTestFargate(app, 'MyTestFargate');

app.synth();
