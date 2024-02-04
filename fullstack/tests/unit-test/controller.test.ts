import { generateShortUriHandler, getUrlByShortIdHandler } from '../../src/controller';
import * as data from '../../src/data'
import * as service from '../../src/service'
import { createMockContext } from '@shopify/jest-koa-mocks'
import { MockShortUriEntity } from '../helpers/mockConstant'

beforeAll(() => {
    jest.spyOn(data, 'saveShortUriEntity').mockResolvedValue()
    jest.spyOn(service, 'getShortId').mockResolvedValue(MockShortUriEntity.shortId)
    jest.spyOn(service, 'getUrlByShortId').mockResolvedValue(MockShortUriEntity.url)
})

afterAll(() => {
    jest.restoreAllMocks()
})

test('test generateShortUriHandler', async () => {

    const mockedlegalCtx = createMockContext({
        requestBody: {
            url: 'test'
        }
    });
    await generateShortUriHandler(mockedlegalCtx, jest.fn());
    expect(mockedlegalCtx.status).toEqual(200);
    expect(mockedlegalCtx.body).toMatchObject({
        uri: `/${MockShortUriEntity.shortId}`
    })

    const mockedCtxWithIllegalCtx = createMockContext({
        requestBody: {
            something: 'xxxx'
        }
    })
    await generateShortUriHandler(mockedCtxWithIllegalCtx, jest.fn())
    expect(mockedCtxWithIllegalCtx.status).toEqual(400)
    expect(mockedCtxWithIllegalCtx.body).toEqual('Illegal Request')
})

test('test getUrlByShortIdHandler', async () => {
    const mockedlegalCtx = createMockContext({
        customProperties: {
            params: {
                shortId: 'ZZZZZZZ'
            }
        }
    })
    // const parsedCtx = await bodyParser()(mockedlegalCtx, jest.fn());
    await getUrlByShortIdHandler(mockedlegalCtx, jest.fn())
    expect(mockedlegalCtx.status).toEqual(200)
    expect(mockedlegalCtx.body).toMatchObject({
        url: MockShortUriEntity.url
    })
    const mockedCtxWithIllegalCtx = createMockContext({
    });
    await getUrlByShortIdHandler(mockedCtxWithIllegalCtx, jest.fn())
    expect(mockedCtxWithIllegalCtx.status).toEqual(404)

})