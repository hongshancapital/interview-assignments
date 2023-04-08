import express, { Express } from 'express';
import { closeDb, createShortUrlTable, loadDb } from './db';
import { createShortUrl, IResult, readShortUrl, StatusCode } from './shortUrl';
import { ShortUrlError } from './ShortUrlError';

function createErrorIResult(msg: string): IResult {
    return { code: StatusCode.Error, msg };
}

const app: Express = express();

// parse application/json
app.use(express.json());

app.post('/shortUrl', async (req, res) => {
    let result: IResult;
    try {
        result = await createShortUrl(req.body);
    } catch (e) {
        if ((e as Error).name === 'ShortUrlError') {
            result = createErrorIResult((e as Error).message);
        } else {
            console.error(e);
            result = createErrorIResult('服务器处理时发生错误！请稍后重试！');
        }
    }

    res.status(200).json(result);
});

app.get('/:shortCode', async (req, res) => {
    let result: IResult;
    try {
        result = await readShortUrl(req.params?.shortCode);
    } catch (e) {
        if (e instanceof ShortUrlError) {
            result = createErrorIResult(e.message);
        } else {
            console.error(e);
            result = createErrorIResult('服务器处理时发生错误！请稍后重试！');
        }
    }
    res.status(200).json(result);
});

/**
 * 初始化资源
 */
async function loadResource() {
    // load db
    // 注意：todo 需要改成配置
    loadDb({
        client: 'mysql',
        connection: {
            host: '192.168.0.130',
            port: 3306,
            user: 'shortUrl',
            password: 'shortUrl',
            database: 'shortUrl',
        },
    });
    await createShortUrlTable();
}

/**
 *  关闭相关资源
 */
async function closeResource() {
    await closeDb();
}

export { app, loadResource, closeResource };
