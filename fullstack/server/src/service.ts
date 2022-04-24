import { PutItemCommand, GetItemCommand } from "@aws-sdk/client-dynamodb";
import { nanoid } from "nanoid";
import { dynamicDbClient } from "./db";
import { tableName } from "./db";
import { getValue, setValue } from "./redisCache";

const defaultUrl = "https://www.github.com/";

export const generateUrl = async (longUrl: string) => {
  const code = nanoid(7);

  const itemData = {
    id: code,
    longUrl,
    code,
    createdAt: { N: `${Date.now()}` },
  };
  const params: any = {
    TableName: tableName,
    Item: {
      id: { S: code },
      longUrl: { S: longUrl },
      code: { S: code },
      createdAt: { N: `${Date.now()}` },
    },
  };

  const result = await dynamicDbClient.send(new PutItemCommand(params));

  return { ...itemData, ...result.$metadata };
};

export const getUrl = async (code: string) => {
  const urlFromCache = await getValue(code);
  if (urlFromCache) {
    return urlFromCache;
  }
  const params = {
    TableName: tableName,
    Key: {
      id: { S: code },
    },
    ProjectionExpression: "longUrl",
  };

  const data = await dynamicDbClient.send(new GetItemCommand(params));

  if (data.Item?.longUrl.S) {
    await setValue(code, data.Item?.longUrl.S);
  }

  return data.Item?.longUrl.S || defaultUrl;
};
