import { Service } from './../service';
import { ShortLinkRepository, IShortLink, createShortLink } from '../db';
import { BloomFilter } from '../bloomfilter';
import { Cache } from '../cache';

jest.mock('../db')
jest.mock('../bloomfilter')
jest.mock('../cache')

const ShortLinkRepositoryMock = ShortLinkRepository as jest.MockedClass<typeof ShortLinkRepository>
const BloomFilterMock = BloomFilter as jest.MockedClass<typeof BloomFilter>
const CacheMock = Cache as jest.MockedClass<typeof Cache>


const mockedslr = jest.mocked(new ShortLinkRepository({} as any))
const mockbf = jest.mocked(new BloomFilter({} as any))
const mockcache = jest.mocked(new Cache({} as any))

const service = new Service(mockcache, mockbf, mockedslr)


beforeEach(() => {
    ShortLinkRepositoryMock.mockClear()
    BloomFilterMock.mockClear()
    CacheMock.mockClear()
})

describe('test hash url', () => {

    test('hash url1', async () => {
        mockbf.hashExist.mockResolvedValue(true)
        mockcache.get.mockResolvedValue('234')
        let ret = await service.hashUrl('123')
        expect(ret).toStrictEqual('234')
    })

    test('hash url2', async () => {
        mockbf.hashExist.mockResolvedValue(false)
        let ret = await service.hashUrl('123')
        expect(ret).toBe(0)
    })

    test('hash url3', async () => {
        mockbf.hashExist.mockResolvedValue(true)
        mockcache.get.mockResolvedValue(null)
        mockcache.tryLock.mockResolvedValue('123')
        mockedslr.findById.mockResolvedValue(undefined)
        let ret = await service.hashUrl('PyRBy')

        expect(ret).toBe(-1)
    })


    test('hash url4', async () => {
        mockbf.hashExist.mockResolvedValue(true)
        mockcache.get.mockResolvedValue(null)
        mockcache.tryLock.mockResolvedValue('123')

        mockedslr.findById.mockResolvedValue({
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/'
        })

        let ret = await service.hashUrl('PyRBy')

        expect(ret).toBe('http://baidu.com/')
    })

    test('hash url5', async () => {

        mockbf.hashExist.mockResolvedValue(true)
        mockcache.get.mockResolvedValueOnce(null)
        mockcache.get.mockResolvedValueOnce('http://baidu.com/')
        mockcache.tryLock.mockResolvedValueOnce(null)
        mockcache.tryLock.mockResolvedValueOnce('123')
        mockedslr.findById.mockResolvedValue({
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/'
        })

        let ret = await service.hashUrl('PyRBy')

        expect(ret).toBe('http://baidu.com/')
    })

    test('hash url6', async () => {

        mockbf.hashExist.mockResolvedValue(true)
        mockcache.get.mockResolvedValue(null)
        mockcache.tryLock.mockResolvedValueOnce(null)
        mockcache.tryLock.mockResolvedValueOnce('123')
        mockedslr.findById.mockResolvedValue(undefined)

        let ret = await service.hashUrl('PyRBy')

        expect(ret).toBe(-1)
    })
})

describe('test service url hash', () => {

    test('url hash1', async () => {
        mockbf.urlExist.mockResolvedValue(true)
        mockedslr.findByUrl.mockResolvedValue({
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/',
            id: 1,
            hash: 'PyRBy'
        })
        let ret = await service.urlHash('http://baidu.com')
        expect(ret).toStrictEqual('PyRBy')
    })

    test('url hash2', async () => {
        mockbf.urlExist.mockResolvedValue(false)
        mockcache.tryLock.mockResolvedValue('123')
        mockedslr.findByUrl.mockResolvedValue(undefined)

        mockedslr.create.mockResolvedValue({
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/',
            id: 1,
        })

        mockedslr.update.mockResolvedValue({
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/',
            id: 1,
            hash: '3244'
        })
        let ret = await service.urlHash('http://baidu.com')
        expect(ret).toStrictEqual('3244')
    })

    test('url hash3', async () => {
        mockbf.urlExist.mockResolvedValue(false)
        mockcache.tryLock.mockResolvedValueOnce(null)
        mockcache.tryLock.mockResolvedValue('123')
        mockedslr.findByUrl.mockResolvedValueOnce({
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/',
            id: 1,
            hash: 'sdg'
        })

        let ret = await service.urlHash('http://baidu.com')
        expect(ret).toStrictEqual('sdg')
    })

    test('url hash4', async () => {
        mockbf.urlExist.mockResolvedValue(false)
        mockcache.tryLock.mockResolvedValueOnce(null)
        mockcache.tryLock.mockResolvedValueOnce('123')

        mockedslr.findByUrl.mockResolvedValue(undefined)

        mockedslr.create.mockResolvedValue({
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/',
            id: 1,
        })

        mockedslr.update.mockResolvedValue({
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/',
            id: 1,
            hash: '5677'
        })
        let ret = await service.urlHash('http://baidu.com')
        expect(ret).toStrictEqual('5677')
    })

    test('url hash5', async () => {
        mockbf.urlExist.mockResolvedValue(true)
        mockedslr.findByUrl.mockResolvedValueOnce(undefined)
        mockedslr.create.mockResolvedValue({
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/',
            id: 1,
        })

        mockedslr.update.mockResolvedValue({
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/',
            id: 1,
            hash: 'dsgdfgdf'
        })
        let ret = await service.urlHash('http://baidu.com')
        expect(ret).toStrictEqual('dsgdfgdf')
    })
})

describe('test service init', () => {
    test('not run', async () => {
        mockbf.exist.mockResolvedValue(true)
        await service.init()
        expect(mockbf.exist).toBeCalled()
    })

    test('run lock fail', async () => {
        mockbf.exist.mockResolvedValue(false)
        mockcache.tryLock.mockResolvedValue(null)
        await service.init()
        expect(mockcache.tryLock).toBeCalled()
    })

    test('run lock success no data', async () => {
        mockbf.exist.mockResolvedValue(false)
        mockcache.tryLock.mockResolvedValue('1234')
        mockedslr.count.mockResolvedValue(0)
        await service.init()
        expect(mockedslr.findByPage).toBeCalled()
    })

    test('run lock success has data', async () => {
        mockbf.exist.mockResolvedValue(false)
        mockcache.tryLock.mockResolvedValue('1234')
        mockedslr.count.mockResolvedValue(500)
        mockedslr.findByPage.mockResolvedValue([{
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/',
            id: 1,
            hash: 'dsgdfgdf'
        }, {
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/',
            id: 2,
        }])
        await service.init()
        expect(mockbf.hashMAdd).toBeCalled()
        expect(mockbf.urlMAdd).toBeCalled()
    })

    test('run lock success has data1', async () => {
        mockbf.exist.mockResolvedValue(false)
        mockcache.tryLock.mockResolvedValue('1234')
        mockedslr.count.mockResolvedValue(1500)
        mockedslr.findByPage.mockResolvedValue([{
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/',
            id: 1,
            hash: 'dsgdfgdf'
        }, {
            constructor: {
                name: 'RowDataPacket'
            },
            domain: 'http://baidu.com',
            path: '/',
            id: 2,
        }])
        await service.init()
        expect(mockbf.hashMAdd).toBeCalled()
        expect(mockbf.urlMAdd).toBeCalled()
    })
})