import { getDomainInfoById, getDomainInfoByLongName, getOneConnection, insertDomainNameInMySql, pool, poolConfig, updateShortCodeInMySql } from '../model/mysql';

describe('app service', () => {
    beforeAll(async () => {
        await pool.query('TRUNCATE TABLE domainNames');
    });
    afterAll(async () => {
        await pool.end();
    });
    it('getOneConnection should return a connection', async () => {
        const conn = await getOneConnection();
        expect(conn.execute).toBeDefined();
        expect(conn.release).toBeDefined();
        conn.release();
    });

    it('insertDomainNameInMySql should insert a domain name', async () => {
        const longName = 'example.com';
        const id = await insertDomainNameInMySql(longName);
        expect(id).toBe(1);
    });
    it('insertDomainNameInMySql should insert a domain name using conn', async () => {
        const longName = 'example.com.2';
        const conn = await getOneConnection();
        const id = await insertDomainNameInMySql(longName, conn);
        conn.release();
        expect(id).toBe(2);
    });

    it('updateShortCodeInMySql should update short name', async () => {
        const id = 1;
        const shortName = 'example';
        const result = await updateShortCodeInMySql(id, shortName);
        expect(result).toBeDefined();
    });
    it('updateShortCodeInMySql should update short name using conn', async () => {
        const id = 1;
        const shortName = 'example';
        const conn = await getOneConnection();
        const result = await updateShortCodeInMySql(id, shortName, conn);
        conn.release();
        expect(result).toBeDefined();
    });

    it('getDomainInfoByLongName should return domain info', async () => {
        const longDomainName = 'example.com';
        const domain = await getDomainInfoByLongName(longDomainName);
        expect(domain).toBeDefined();
        expect(domain?.id).toBe(1);
        expect(domain?.longName).toBe(longDomainName);
    });
    it('getDomainInfoByLongName should return domain info using conn', async () => {
        const longDomainName = 'example.com';
        const conn = await getOneConnection();
        const domain = await getDomainInfoByLongName(longDomainName, conn);
        conn.release();
        expect(domain).toBeDefined();
        expect(domain?.id).toBe(1);
        expect(domain?.longName).toBe(longDomainName);
    });
    it('getDomainInfoByLongName should return undefined if longName is not exist', async () => {
        const longDomainName = 'example.com.1';
        const domain = await getDomainInfoByLongName(longDomainName);
        expect(domain).toBeUndefined();
    });

    it('getDomainInfoById should return domain info', async () => {
        const id = 1;
        const domain = await getDomainInfoById(id);
        expect(domain).toBeDefined();
        expect(domain?.id).toBe(id);
        expect(domain?.longName).toBe('example.com');
        expect(domain?.shortName).toBe('example');
    });
    it('getDomainInfoById should return domain info using conn', async () => {
        const id = 1;
        const conn = await getOneConnection();
        const domain = await getDomainInfoById(id, conn);
        conn.release();
        expect(domain).toBeDefined();
        expect(domain?.id).toBe(id);
        expect(domain?.longName).toBe('example.com');
        expect(domain?.shortName).toBe('example');
    });
    it('getDomainInfoById should return undefined if id is not exist', async () => {
        const id = 3;
        const domain = await getDomainInfoById(id);
        expect(domain).toBeUndefined();
    });
});