import { AppDataSource } from "../../data-source"
import { ShortUrl } from "../../entity/ShortUrl"
import { processEnv } from '../../app-config'

export default class ShortUrlDBService {

    static async getByLongUrl(long_url: string): Promise<ShortUrl | null> {
        const repository = AppDataSource.getRepository(ShortUrl)
        return await repository.findOneBy({
            long_url,
        })
    }

    static async getByid(id: number): Promise<ShortUrl | null> {
        const repository = AppDataSource.getRepository(ShortUrl)
        return await repository.findOneBy({
            id,
        })
    }

    static async create(long_url: string): Promise<ShortUrl> {
        const repository = AppDataSource.getRepository(ShortUrl)
        return await repository.save({
            long_url,
        })
    }

    static async maxId(): Promise<number> {
        const repository = AppDataSource.getRepository(ShortUrl)
        const { maxShortUrlId } = await repository.createQueryBuilder('shorturl').select('MAX(shorturl.id)', 'maxShortUrlId').getRawOne()
        return maxShortUrlId
    }

}
