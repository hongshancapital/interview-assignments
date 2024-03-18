import { getShortUrl, getOrginalUrl } from '../short_url_service';

const mockControl = {
    queryMockImpl: (args: any[]): any => Promise.resolve([]),
};

jest.mock('../db', () => {
    return {
        getDbConnection: jest.fn().mockResolvedValue({
            release: jest.fn(),
        }),
        query: jest.fn((...args) => mockControl.queryMockImpl(args)),
    };
});

describe('test short URL service', () => {
    test('test getShortUrl with new URL', async () => {
        mockControl.queryMockImpl = async (args: any[]) => {
            if(args[1].startsWith('SELECT')){
                return Promise.resolve([]);
            } else if (args[1].startsWith('INSERT')){
                return Promise.resolve({insertId: 10598});
            } 
        };
        try{
            const shortUrl = await getShortUrl('www.aaa.com');
            expect(shortUrl).toBe('Eav');
        } catch(e) {
            fail(e);
        }
    });

    test('test getShortUrl with existing URL', async () => {
        const testUrl = 'www.aaa.com';
        mockControl.queryMockImpl = async (args: any[]) => {
            return Promise.resolve([{id: 10598, url: testUrl}]);
        };
        try{
            const shortUrl = await getShortUrl(testUrl+' ');
            expect(shortUrl).toBe('Eav');
        } catch(e) {
            fail(e);
        }
    });

    test('test getShortUrl with one conflict URL', async () => {
        let selectCount = 0;
        mockControl.queryMockImpl = async (args: any[]) => {
            if(args[1].startsWith('SELECT')){
                selectCount ++;
                if(selectCount < 2) {
                    return Promise.resolve([{id: 10122, url: 'some.url.com'}]);
                } else {
                    return Promise.resolve([]);
                }
                
            } else if (args[1].startsWith('INSERT')){
                return Promise.resolve({insertId: 10598});
            } 
        };
        try{
            const shortUrl = await getShortUrl('www.aaa.com');
            expect(shortUrl).toBe('Eav');
        } catch(e) {
            fail(e);
        }
    });

    test('test getShortUrl with too many conflict URLs', async () => {
        mockControl.queryMockImpl = async (args: any[]) => {
            if(args[1].startsWith('SELECT')){
                return Promise.resolve([{id: 10122, url: 'some.url.com'}]);
            } else if (args[1].startsWith('INSERT')){
                return Promise.resolve({insertId: 10598});
            } 
        };
        await expect(getShortUrl('www.aaa.com')).rejects.toBeTruthy();
    });

    test('test getOrginalUrl successful', async () => {
        const url = 'www.aaa.com';
        mockControl.queryMockImpl = async (args: any[]) => {
            return Promise.resolve([{url}]);
        };
        await expect(getOrginalUrl('Eav')).resolves.toBe(url);
    });

    test('test getOrginalUrl not found', async () => {
        mockControl.queryMockImpl = async (args: any[]) => {
            return Promise.resolve([]);
        };
        await expect(getOrginalUrl('Eav')).resolves.toBe('');
    });

    test('test getOrginalUrl with wrong input', async () => {
        mockControl.queryMockImpl = async (args: any[]) => {
            return Promise.resolve([]);
        };
        await expect(getOrginalUrl('Ea.')).rejects.toBe('Illegal short URL');
    });
});