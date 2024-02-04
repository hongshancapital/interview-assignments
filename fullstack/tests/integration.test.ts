
import request from 'supertest'
import waitOn from 'wait-on';
import { spawn, execSync } from 'child_process';
import * as process from 'process'
import { APP_PORT } from '../src/constant';
import { dataSource, setupDB } from '../src/data/datasource'
import { ShortUriEntity } from '../src/data/entities/ShortUriEntity'

execSync('yarn build')
const serverChildProcess = spawn('node', ['dist/app.js'], { cwd: process.cwd(), detached: true });
serverChildProcess.on('error', err => {
    console.log('[server start up err]', err)
    serverChildProcess.kill();
    process.exit(1)
})

const req = request(`localhost:${APP_PORT}`)
const url = 'www.test.com'
beforeAll(async () => {
    await setupDB()
    await waitOn({ resources: [`tcp:${APP_PORT}`] })
})

afterAll(async () => {
    if (!serverChildProcess.killed && serverChildProcess.pid) {
        process.kill(serverChildProcess.pid)
    }

    await dataSource.dropDatabase()
    await dataSource.destroy();
})



test('integration test for api /generate-short-uri', async () => {
    const resp = await req.post('/generate-short-uri').send({
        url
    })
    expect(resp.body).toHaveProperty('uri')
    expect(resp.statusCode).toEqual(200)
})

test('interagtion test for api /:shortId', async () => {
    await req.get('/test-something').expect(404);
    const records = await dataSource.getRepository(ShortUriEntity).find()
    if (records.length !== 0) {
        const getUrlResp = await req.get(`/${records[0].shortId}`)
        expect(getUrlResp.status).toEqual(200)
        expect(getUrlResp.body).toHaveProperty('url')
    } else {
        const getShortUriResp = await req.post('/generate-short-uri').send({
            url
        });
        const getUrlResp = await req.get(`/${getShortUriResp.body.uri.substring(1)}`)
        expect(getUrlResp.status).toEqual(200)
        expect(getUrlResp.body).toHaveProperty('url')
    }
})