import express, { Express } from 'express';
import { closeDb, createShortUrlTable, loadDb } from './db';
import { createErrorIResult, createShortUrl, IResult } from './shortUrl';
import { ShortUrlError } from './ShortUrlError';
const app: Express = express();

// parse application/json
app.use(express.json());

app.post('/shortUrl', async (req, res) => {
    let result: IResult;
    try {
        result = await createShortUrl(req.body);
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

app.get('/:shortCode', (req, res) => {
    res.status(200).json(req.params);
});

/**
 * 初始化资源
 */
async function loadResource() {
    // load db
    // 注意：todo 需要改成配置
    // loadDb({
    //     client: 'sqlite3',
    //     connection: {
    //         filename: './data.db',
    //     },
    // });
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
