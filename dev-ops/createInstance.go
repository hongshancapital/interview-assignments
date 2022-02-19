package main

import (
	"os"

	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/ec2"

	"fmt"
)

func CreateInstance(client *ec2.EC2, imageId string, minCount int, maxCount int, instanceType string, keyName string) (*ec2.Reservation, error) {
	res, err := client.RunInstances(&ec2.RunInstancesInput{
		ImageId:      aws.String(imageId),
		MinCount:     aws.Int64(int64(minCount)),
		MaxCount:     aws.Int64(int64(maxCount)),
		InstanceType: aws.String(instanceType),
		KeyName:      aws.String(keyName),
	})

	if err != nil {
		return nil, err
	}

	return res, nil
}


func main() {
	sess, err := session.NewSessionWithOptions(session.Options{
		Profile: "default",
		Config: aws.Config{
			Region: aws.String("us-west-2"),
		},
	})

	if err != nil {
		fmt.Printf("Failed to initialize new session: %v", err)
		return
	}

	ec2Client := ec2.New(sess)

	keyName := "ec2-go-tutorial-key-name"
	instanceType := "t4g.nano"
	minCount := 1
	maxCount := 1
	imageId := "ami-0b0154d3d8011b0cd"
	newInstance, err := CreateInstance(ec2Client, imageId, minCount, maxCount, instanceType, keyName)
	if err != nil {
		fmt.Printf("Couldn't create new instance: %v", err)
		return
	}

	fmt.Printf("Created new instance: %v\n", newInstance.Instances)
}