import { DynamoDBClient } from "@aws-sdk/client-dynamodb";

// Set the AWS Region.
const REGION = "us-east-1";
// Create an Amazon DynamoDB service client object.
const dynamicDbClient = new DynamoDBClient({ region: REGION });
// Use other DB...
export { dynamicDbClient };
