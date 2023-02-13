import { PrismaClient, Prisma } from "@prisma/client";

const dbClient = new PrismaClient();

dbClient.$connect();

export default dbClient;