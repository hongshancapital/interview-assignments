import { Domains } from '@/models';
import { Redis } from '@/services';

export default new class DomainDal {
    constructor() {
        //
    }

    async getOriginalDomain(shortDomain: string): Promise<string | null> {
        const cached = await Redis.getDomain(shortDomain);

        if (cached) {
            return cached.url;
        }
        const dbData = await Domains.findOne({ compressed: shortDomain });

        if (dbData) {
            await Redis.setDomain(shortDomain, dbData.url);

            return dbData.url;
        }
        return null;
    }

    async getList(option: { skip?: number, limit?: number, name?: string }): Promise<{ list: Array<DomainModel>, total: number }> {
        const { skip = 0, limit = 10, name } = option;

        return await Domains.paging({
            ...name ? { name } : {}
        }, limit, skip);
    }

    async insert(data: { name?: string, url: string, compressed: string, type: 'default' | 'custom' }) {
        const { name, url, compressed, type } = data;

        return await Domains.insertOne({ name, url, compressed, type } as DomainModel);
    }

    async findById(_id: string) {
        return await Domains.findById(_id);
    }

    async findByOriginalUrl(originalUrl: string) {
        return await Domains.findOne({ url: originalUrl });
    }
};
