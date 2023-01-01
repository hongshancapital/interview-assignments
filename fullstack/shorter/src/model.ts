import { PrismaClient } from "@prisma/client";
import test from "node:test";

const prisma = new PrismaClient();

export async function findUrlById(id: string) {
  let myUrl = await prisma.urls.findUnique({
    where: {
      id: parseInt(id),
    },
  });
  return myUrl;
}

export async function findUrlByUrl(url: string) {
  let myUrl = await prisma.urls.findUnique({
    where: {
      url: url,
    },
  });
  return myUrl;
}

export async function insertUrlAndGetId(url: string) {
  let myUrl = await prisma.urls.findUnique({
    where: {
      url: url,
    },
  });
  if (!(null === myUrl)) 
    return myUrl;

  try {
    let myUrl = await prisma.urls.create({
      data: {
        url: url,
      },
    });
    return myUrl;
  } catch (error) {
    return null;
  }
}

async function findAllUrl() {
  let myUrl = await prisma.urls.findMany({
    take: 100,
  });
  console.log(myUrl);
}

function test1() {
  try {
    insertUrlAndGetId("https://bing.com").then(console.log, console.log);
  } catch (error) 
  {}
}
//test1()