import { DynamoDBClient } from "@aws-sdk/client-dynamodb";

import {
  CreateTableCommand,
  ListTablesCommand,
} from "@aws-sdk/client-dynamodb";

// Set the AWS Region.
const REGION = "us-east-1";

// Create an Amazon DynamoDB service client object.
export const dynamicDbClient = new DynamoDBClient({ region: REGION });

// init db
export const tableName: string = "tinyUrls";

export const createTable = async (): Promise<any> => {
  const { TableNames } = await getTableList();

  if (TableNames?.includes(tableName)) {
    return;
  }
  const params = {
    AttributeDefinitions: [
      {
        AttributeName: "id",
        AttributeType: "S",
      },
    ],
    KeySchema: [
      {
        AttributeName: "id",
        KeyType: "HASH",
      },
    ],
    TableName: tableName,
    ProvisionedThroughput: {
      ReadCapacityUnits: 1,
      WriteCapacityUnits: 1,
    },
  };

  try {
    const data = await dynamicDbClient.send(new CreateTableCommand(params));
  } catch (err) {
    console.log("Error", err);
  }
};

export const getTableList = async () => {
  return dynamicDbClient.send(new ListTablesCommand({}));
};
