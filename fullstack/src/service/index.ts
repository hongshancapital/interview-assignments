import {PrismaClient, Prisma} from "@prisma/client";
import to from 'await-to-js';
const prisma = new PrismaClient();

async function findByUrl(url: string) {
    const [ err, record ] = await to(prisma.shortUrl.findFirst({
        where: {
            longUrl: url
        }
    }));
    return [ err, record ];
}

async function findByShort(short: string) {
    const [ err, record ] = await to(prisma.shortUrl.findUnique({
        where: {
            urlCode: short
        }
    }));
    return [ err, record ];
}

async function create(urlCode: string, longUrl: string) {
    const [ err, record ] = await to(prisma.shortUrl.create({
        data: {
            urlCode,
            longUrl
        }
    }))
    return [ err, record ]
}

export {
    findByUrl,
    findByShort,
    create
}