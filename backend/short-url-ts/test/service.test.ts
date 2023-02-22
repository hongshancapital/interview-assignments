import {UrlService} from '../src/api/service';
import {UrlDao} from '../src/api/dao';
import {connectMasterDb, closeDbConnection} from '../src/db/PostgreSql';
import {connectRedis, closeRedis} from '../src/db/redis';
import Config from '../src/config/config';
import * as dbConfig from '../src/config/config_datasource';

const urlService = new UrlService();
const urlDao = new UrlDao();

const longUrl: string = 'https://long.com/longlonglong76856534278';
let shortUrl: string;
let errorCode: string;

describe('connect db', () => {
    test('connect db', (done) => {
        (async () => {
            // connect pgsql
            await connectMasterDb();

            // connect redis
            await connectRedis();
            done();

        })().catch((err: Error) => {
            console.error(err);
        });
    });
});

describe('cvtIdToShortUrl', () => {
    test('cvtIdToShortUrl', () => {
        expect(urlService.cvtIdToShortUrl(123456789012345)).toBe("https://st.com/TPXpgyeC");
    });
});

describe('putUrl', () => {
    test('正常长链接', async () => {
        shortUrl = await urlService.putUrl(longUrl);
        expect(shortUrl).toMatch(/[0-9a-zA-Z]/);
    });
    test('已存在的长链接', async () => {
        const shortUrl2 = await urlService.putUrl(longUrl);
        expect(shortUrl2).toBe(shortUrl);
    });
    test('链接为空', async () => {
        await urlService.putUrl('').catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('406_missing_param');
    });
    test('链接格式错误', async () => {
        await urlService.putUrl('htt//long.com/longlonglong999').catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('406_wrong_url');
    });
});

describe('getLongUrl', () => {
    test('正常短链接，首次被读取', async () => {
        expect(await urlService.getLongUrl(shortUrl)).toBe(longUrl);
    });
    test('正常短链接，非首次被读取', async () => {
        expect(await urlService.getLongUrl(shortUrl)).toBe(longUrl);
    });
    test('链接参数为空', async () => {
        await urlService.getLongUrl('').catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('406_missing_param');
    });
    test('链接格式错误', async () => {
        await urlService.getLongUrl('htt//st.com/TPXpgyeC').catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('406_wrong_url');
    });
    test('链接域名错误', async () => {
        await urlService.getLongUrl('https://stwrong.com/TPXpgyeC').catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('406_wrong_host');
    });
    test('链接短码为空', async () => {
        await urlService.getLongUrl('https://st.com/').catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('406_unknown_url');
    });
    test('链接短码含非法字符', async () => {
        await urlService.getLongUrl('https://st.com/TPXp?&ye').catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('406_unknown_url');
    });
    test('链接短码超出8位', async () => {
        await urlService.getLongUrl('https://st.com/123456789').catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('406_unknown_url');
    });
    test('链接短码不存在', async () => {
        await urlService.getLongUrl('https://st.com/ZZZZZZZZ').catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('404_not_hit');
    });
});

describe('closedb', () => {
    test('延时等待2秒', (done) => {
        setTimeout(() => {
            done();
        }, 2000)
    });
    test('closedb', (done) => {
        (async () => {
            // pgsql db stop
            await closeDbConnection();
            // redis stop
            await closeRedis();
            done()

        })().catch((err: Error) => {
            console.error(err);
        });
    });
});

describe('other:for 100%', () => {
    test('config缺少配置：短链接固定域名', async () => {
        Config.shortHost = '';
        await urlService.putUrl(longUrl).catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('500_config_err');
    });
    test('dao:创建时给非空参数传空值', async () => {
        await urlDao.creatUrl('').catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('500_missing_param');
    });
    test('pgsql:连接失败', async () => {
        dbConfig.dataBase.password = 'wrong';
        await connectMasterDb().catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('500_pgsql_err');
    });
    test('redis:配置缺失', async () => {
        dbConfig.redis.port = 0;
        await connectRedis().catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('500_config_err');
    });
    jest.setTimeout(10000);
    test('redis:连接失败', async () => {
        dbConfig.redis.port = 1;
        await connectRedis().catch(err => {
            errorCode = err && err.errorCode;
        });
        expect(errorCode).toBe('500_redis_err');
    });
});