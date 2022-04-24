import {
  CreateTableCommand,
  ListTablesCommand,
} from "@aws-sdk/client-dynamodb";
import { dynamicDbClient } from "./dbClients";

export const tableName: string = "tinyUrls";

export const createTable = async (): Promise<any> => {
  const { TableNames } = await getTableLst();
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

export const getTableLst = async () => {
  return dynamicDbClient.send(new ListTablesCommand({}));
};
