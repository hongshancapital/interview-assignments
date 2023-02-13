import dbClient from "./database";
import redis from "./cache";
import { PrismaClientKnownRequestError } from "@prisma/client/runtime";

class ShortLinkModel {
    static readonly ExpireTime = 2 * 60 * 60;

    private key: string;
    private originLink?: string;

    constructor(key: string, originLink?: string) {
        this.key = key;
        this.originLink = originLink;
    }

    async get() {
        let originLink = null;
        let error = null;
        try {
            originLink = await this.getShortLinkFromRedis();
            if (!originLink) {
                try {
                    const shortLinkModel = await this.getShortLinkFromDatabase();
                    this.originLink = shortLinkModel?.originLink || '';
                    this.addShortLinkToRedis();
                    originLink = shortLinkModel?.originLink || null;
                } catch (error) {
                    console.error(error)
                    return {originLink, error}
                }
            }
        } catch(error) {
            console.warn(error)
            const shortLinkModel = await this.getShortLinkFromDatabase();
            originLink = shortLinkModel?.originLink || null;
        }
        return {originLink, error}
    }

    async add():Promise<Boolean> {
        try {
            await this.addShortLinkToDatabase()
        } catch(e: any) {
            if (e instanceof PrismaClientKnownRequestError) {
                if (e.code === 'P2002') {
                  return true;
                }
              }
              throw e;
        }

        this.addShortLinkToRedis();
        return false;
    }

    private async getShortLinkFromDatabase() {
        return await dbClient.short_link.findUnique({
            where: {key: this.key},
        })
    }

    private async getShortLinkFromRedis() {
        return await redis.get(this.key || '')
    }

    private async addShortLinkToDatabase() {
        return await dbClient.short_link.create(
            {
                data: {
                    key: this.key,
                    originLink: this.originLink || '',
                }
            }
        )
    }

    private async addShortLinkToRedis() {
        try {
            await redis.set(this.key, this.originLink || '', 'EX', ShortLinkModel.ExpireTime)
        } catch {
            console.warn("save short link to redis failed")
        }
    }
}

export default ShortLinkModel;