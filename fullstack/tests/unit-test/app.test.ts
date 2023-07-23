import { DataSource } from 'typeorm';
import request from 'supertest';
import Koa from 'koa';
import * as db from '../../src/data/datasource';
import { MockShortUriEntity } from '../helpers/mockConstant';
import { APP_PORT } from '../../src/constant';
// import { appPromise } from '../src/app'

const appListen = jest.fn()
let spyOnSetupDB: jest.SpyInstance
let appPromise: Promise<Koa>
beforeEach(async () => {
    jest.spyOn(Koa.prototype, 'listen').mockImplementation(appListen);
    jest.spyOn(db.dataSource, 'getRepository').mockImplementation(() => {
        return {
            save: jest.fn().mockResolvedValue(MockShortUriEntity),
            findOneBy: jest.fn().mockResolvedValue(MockShortUriEntity)
        } as any
    })

    spyOnSetupDB = jest.spyOn(db, 'setupDB').mockImplementation(() => {
        return Promise.resolve({
            isInitialized: true
        } as DataSource)
    })
    appPromise = require('../../src/app').appPromise
})

afterEach(() => {
    jest.restoreAllMocks();
})


test('App started', async () => {

    await appPromise.then((app) => {
        expect(appListen).toBeCalledTimes(1);
        expect(appListen.mock.calls[0][0]).toEqual(APP_PORT)
    })

})

test('App works', async () => {
    await appPromise.then(async (app) => {
        await request(app.callback()).get('/AAAA').expect(200)
        const response = await request(app.callback()).post('/generate-short-uri').send({
            url: 'www.test.cn'
        })
        expect(response.status).toEqual(200)
        expect(response.body).toHaveProperty('uri')
    })


})