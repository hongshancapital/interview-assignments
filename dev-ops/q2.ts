import ec2 = require('@aws-cdk/aws-ec2');
import ecs = require('@aws-cdk/aws-ecs');
import ecs_patterns = require('@aws-cdk/aws-ecs-patterns');
import cdk = require('@aws-cdk/core');

class testFargate extends cdk.Stack {
  constructor(scope: cdk.App, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    // Create VPC and Fargate Cluster
    const vpc = new ec2.Vpc(this, 'MyVpc', { 
      maxAzs: 2,
      subnetConfiguration: [
      {
        cidrMask: 24,
        name: 'app',
        subnetType: ec2.SubnetType.PRIVATE
      }
      ] 
    });
    const cluster = new ecs.Cluster(this, 'Cluster', { vpc });

    // Instantiate Fargate Service with just cluster and image
    new ecs_patterns.ApplicationLoadBalancedFargateService(this, "FargateService", {
      cluster,
      memoryLimitMiB: 512,
      cpu: 256,
      publicLoadBalancer: false,
      taskImageOptions: {
        image: ecs.ContainerImage.fromRegistry("amazon/amazon-ecs-sample"),
      },
    });
  }
}

const app = new cdk.App();

new testFargate(app, 'testcdk');

app.synth();