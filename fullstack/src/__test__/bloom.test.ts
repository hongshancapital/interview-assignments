import { optimalFilterSize, optimalHashes, BloomFilter } from '../src/bloomFilter';
import { storeInstance } from '../src/store';

let u32 = new Uint32Array(2)

describe('func test', () => {
    it('optimalFilterSize', () => {
        expect(optimalFilterSize(100000000, 0.01)).toBe(958505838);
    })
    it('optimalHashes', () => {
        expect(optimalHashes(958505838, 100000000)).toBe(7);
    })
})


describe('bloom conflict', () => {
    const OLD_ENV = process.env;

    beforeEach(() => {
        jest.resetModules()
        process.env = { ...OLD_ENV };
    });

    afterAll(() => {
        process.env = OLD_ENV;
    });

    it('conflict', () => {
        process.env.BF_SIZE = '10';
        process.env.BF_FPP = '0.9';
        let si = require('../src/store').storeInstance;
        for (let i = 0; i < 100; i++) {
            si.save(i.toString(), i.toString());
        }
        expect(si.has('hello')).toBe(true);
        expect(si.load('hello')).toBe(undefined);
    })
})

describe('bloom test', () => {


    it('bloom error', () => {
        expect(() => { new BloomFilter(4294967294, 1) }).toThrowError('fpp must be less than 1');
    })

    it('bloom create', () => {
        var bf = new BloomFilter(4294967294, 0.01);
        expect(bf).toBeInstanceOf(BloomFilter);
        expect(bf.byteSize).toBe(1286484758);
        let url = 'https://www.baidu.com'
        let locations = bf.getLocations(url);
        let arr = [4122120068,
            5039864987,
            5957609906,
            6875354825,
            7793099744,
            8710844663,
            9628589582]
        expect(locations).toEqual(arr)

        bf.add(url)
        let buckets = bf.getBuckets()
        arr.forEach((item) => {
            let idx = Math.floor(item / 32)
            u32[0] = 1 << (item % 32)
            expect(buckets[idx]).toBe(u32[0])
        })
    })

    it('bloom create2', () => {
        var bf = new BloomFilter(1000, 0.01);
        expect(bf).toBeInstanceOf(BloomFilter);
        expect(bf.byteSize).toBe(300);
        let url: string, locations: number[], arr: number[], buckets: Uint32Array;

        url = 'ab'
        locations = bf.getLocations(url);
        arr = [7391,
            8004,
            8617,
            9230,
            243,
            856,
            1469]
        expect(locations).toEqual(arr)


        bf.add(url)
        buckets = bf.getBuckets()
        arr.forEach((item) => {
            let idx = Math.floor(item / 32)
            u32[0] = 1 << (item % 32)
            if (idx === 25) {
                u32[1] |= 1 << (item % 32)
            }
            expect(buckets[idx]).toBe(u32[0])
        })
        expect(bf.has(url)).toBe(true)
        expect(bf.has('hello')).toBe(false)



        url = 'helloc'
        locations = bf.getLocations(url);

        bf.add(url)
        buckets = bf.getBuckets()
        arr.forEach((item) => {
            let idx = Math.floor(item / 32)
            u32[0] = 1 << (item % 32)
            if (idx === 45) {
                u32[1] |= 1 << (item % 32)
                expect(buckets[idx]).toBe(u32[1])
            } else {
                expect(buckets[idx]).toBe(u32[0])
            }

        })

        expect(bf.has(url)).toBe(true)
        expect(bf.has(url + "\n")).toBe(false)


    })
})