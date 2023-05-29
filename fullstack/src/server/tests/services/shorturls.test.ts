import {afterAll, beforeAll, describe, expect, test} from '@jest/globals';
import {
    CAPACITY,
    indexToBase58String,
    base58StringToIndex,
    createShortUrl,
    getLongUrl,
    isValidUrl
} from "../../services/shorturl";
import * as db from "../../db";
import redisCache, * as redis from "../../redisCache";

beforeAll(async () => {
    await db.init();
    await redis.init();
});

afterAll(async () => {
    await db.close();
    await redisCache.del("ShortUrl::*");
    await redis.close();
});

describe('base58Encode', () => {
    test('it creates a base58 from an int', () => {
        expect(indexToBase58String(0)).toBe("1");
    });

    test('it creates a base58 from the 2nd smallest', () => {
        expect(indexToBase58String(1)).toBe("2");
    });

    test('it creates a base58 from the 57th smallest', () => {
        expect(indexToBase58String(57)).toBe("z");
    });

    test('it creates a longer base58 from an int', () => {
        expect(indexToBase58String(59)).toBe("12");
    });

    test('it creates a longer base58 (69) from an int', () => {
        expect(indexToBase58String(69)).toBe("1C");
    });

    test('it creates the maximum base58 from an int', () => {
        expect(indexToBase58String(CAPACITY - 1)).toBe("zzzzzzzz");
    });

    test('it throws if it exceeds the range', () => {
        expect(() => indexToBase58String(CAPACITY)).toThrow(RangeError);
    });

    test('it throws if it is below the range', () => {
        expect(() => indexToBase58String(-5)).toThrow(RangeError);
    });
});

describe('base58Decode', () => {
    test('it decodes a base58 to an int', () => {
        expect(base58StringToIndex("1")).toBe(0);
    });

    test('it creates a base58 from the 2nd smallest', () => {
        expect(base58StringToIndex("2")).toBe(1);
    });

    test('it creates a base58 from the 57th smallest', () => {
        expect(base58StringToIndex("z")).toBe(57);
    });

    test('it creates a longer base58 from an int', () => {
        expect(base58StringToIndex("12")).toBe(59);
    });

    test('it creates a longer base58 (69) from an int', () => {
        expect(base58StringToIndex("1C")).toBe(69);
    });

    test('it creates the maximum base58 from an int', () => {
        expect(base58StringToIndex("zzzzzzzz")).toBe(CAPACITY - 1);
    });

    test('it throws if it exceeds the range', () => {
        expect(() => base58StringToIndex("LOLOLO")).toThrow(Error);
    });
});

describe('create and retrieve short urls', () => {
    test("it rejects invalid url (bad protocol)", () => {
        expect(isValidUrl("javascript:void(0)")).toBeFalsy();
    });

    test("it rejects invalid url (too long)", () => {
        const part = new Array(2048).fill("a").join("");
        expect(isValidUrl(`https://example.com/${part}`)).toBeFalsy();
    });

    test("it rejects invalid url (bad type)", () => {
        expect(isValidUrl(false)).toBeFalsy();
    });

    test('it creates short urls', async () => {
        const longUrl = "https://163.com";
        const shortUrl = await createShortUrl(longUrl);
        expect(await getLongUrl(shortUrl)).toBe(longUrl);
    });

    test('it retrieves short urls from cache first', async () => {
        const shortUrl = "TEST";
        const cacheKey = `ShortUrl::${shortUrl}`;
        await redisCache.set(cacheKey, "FAKE");
        expect(await getLongUrl(shortUrl)).toBe("FAKE");
    });

    test('it retrieves nothing if not created', async () => {
        const shortUrl = "zzzzzzz";
        expect(await getLongUrl(shortUrl)).toBe(null);
    });

    test('it retrieves nothing if empty string', async () => {
        const shortUrl = "";
        expect(await getLongUrl(shortUrl)).toBe(null);
    });
});
