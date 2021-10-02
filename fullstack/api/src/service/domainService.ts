import { Domain } from '../entity/domain';
import { customAlphabet } from 'nanoid'
import { getConnection } from 'typeorm';


export async function getUrlByDomain(domain: string): Promise<Domain | undefined> {
    const item = await getConnection().getRepository(Domain).findOne({ where: { domain: domain } });
    if (item) {
        return item;
    }
    const url = transferDomainToShortUrl(domain);
    const result = await getConnection().getRepository(Domain).save({ id: url, domain: domain, url: url });
    return result;
}

export async function getDomainByUrl(url: string): Promise<Domain | undefined> {
    return await getConnection().getRepository(Domain).findOne({ where: { url: url } });
}

export function transferDomainToShortUrl(domain: string): string {
    const nanoid = customAlphabet('23456789abcdefghijkmnpqrstuvwxyzABCDEFGHIJKLMNPQRSYUVWXYZ', 8);
    return nanoid();
}