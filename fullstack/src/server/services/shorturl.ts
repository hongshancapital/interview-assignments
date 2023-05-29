
import db, {UNIQUE_VIOLATION} from "../db";
import {DatabaseError} from "pg";
import redisCache from "../redisCache";

const ENCODING_RADIX = 58;

const CAPACITIES = [
    ENCODING_RADIX ** 1,
    ENCODING_RADIX ** 2,
    ENCODING_RADIX ** 3,
    ENCODING_RADIX ** 4,
    ENCODING_RADIX ** 5,
    ENCODING_RADIX ** 6,
    ENCODING_RADIX ** 7,
    ENCODING_RADIX ** 8,
];
const PREFIX_CAPACITY = CAPACITIES.reduce((s, i) => {
    return [...s, s[s.length - 1] + i];
}, [0]);
export const CAPACITY = PREFIX_CAPACITY[PREFIX_CAPACITY.length - 1]; // CAPACITY = 130309802449910 < Number.MAX_SAFE_INTEGER
const CHARACTERS = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
const CHARACTER_INDEX = CHARACTERS.split("").reduce<{
    [key: string]: number,
}>((result, char, i) => {
    result[char] = i;
    return result;
}, {});

function divmod(x: number, y: number): number[] {
    return [Math.floor(x / y), x % y];
}

/**
 * Convert a position in the overall value space to its base58 form.
 * @param num: starting at 0, cannot be greater than CAPACITY - 1.
 */
export function indexToBase58String(num: number): string {
    if (num < 0 || num >= CAPACITY) {
        throw new RangeError("Base58 Input not in range");
    }

    let result = "";
    let div = num + 1;
    let mod = null;
    while (div > 0) {
        div--;
        [div, mod] = divmod(div, ENCODING_RADIX);
        result = CHARACTERS[mod] + result;
    }

    return result;
}

export function base58StringToIndex(shortUrl: string): number {
    let result = 0;
    for (const char of shortUrl) {
        const pos = CHARACTER_INDEX[char];
        if (pos >= 0) {
            result = ENCODING_RADIX * result + pos;
        } else {
            throw new Error("shortUrl is not valid");
        }
    }
    result += PREFIX_CAPACITY[shortUrl.length - 1];
    return result;
}

/*
const RANDOM_COLLISION_MAX_RETRY = 3;

export async function getRandomLongUrl(shortUrl: string): Promise<string | null> {
    if (!shortUrl) {
        return null;
    }
    const result = await db.query("SELECT longurl FROM shorturls WHERE shorturl = $1 LIMIT 1;", [shortUrl]);
    if (result.rows.length) {
        return result.rows[0].longurl ?? null;
    } else {
        return null;
    }
}

function getRandomShortUrlInt(): number {
    return Math.floor(Math.random() * CAPACITY);
}

async function createRandomShortUrl(longUrl: string): Promise<string> {
    for (let _i = 0; _i < RANDOM_COLLISION_MAX_RETRY; _i++) {
        const shortUrl = indexToBase58String(getRandomShortUrlInt());
        try {
            await db.query("INSERT INTO shorturls(shortUrl, longUrl) VALUES($1, $2) RETURNING id;",
                [shortUrl, longUrl],
            );

            return shortUrl;
        } catch (error) {
            if (error instanceof DatabaseError) {
                if (error.code !== UNIQUE_VIOLATION) {
                    throw error;
                }
            }
        }
    }
    throw new Error("Too many random collisions");
}
*/

async function getAutoIncLongUrl(shortUrl: string): Promise<string | null> {
    if (!shortUrl) {
        return null;
    }
    const id = base58StringToIndex(shortUrl);
    const result = await db.query("SELECT longurl FROM autoinc_shorturls WHERE id = $1 LIMIT 1;", [id]);
    if (result.rows.length) {
        return result.rows[0].longurl;
    } else {
        return null;
    }
}

async function createAutoIncShortUrl(longUrl: string): Promise<string> {
    const { rows } = await db.query("INSERT INTO autoinc_shorturls(longUrl) VALUES($1) RETURNING id;",
        [longUrl],
    );

    return indexToBase58String(parseInt(rows[0].id));
}

export function isValidUrl(longUrl: unknown): boolean {
    if (typeof longUrl !== "string") {
        return false;
    }
    if (longUrl.length > 1024) {
        return false;
    }
    return longUrl.startsWith("https://") || longUrl.startsWith("http://");
}

function saveToCacheNonBlocking(cacheKey: string, longUrl: string) {
    setImmediate(async () => {
        try {
            await redisCache.set(cacheKey, longUrl, {
                EX: 7200,
            });
        } catch (e) {
            /* istanbul ignore next */
            console.error(e);
        }
    });
}

export async function getLongUrl(shortUrl: string): Promise<string | null> {
    const cacheKey = `ShortUrl::${shortUrl}`;
    const cached = await redisCache.get(cacheKey);
    if (cached) {
        return cached;
    }
    const result = await getAutoIncLongUrl(shortUrl); // getRandomLongUrl(shortUrl);
    if (result) {
        saveToCacheNonBlocking(cacheKey, result);
   }
   return result;
}

export async function createShortUrl(longUrl: string): Promise<string> {
    // return createRandomShortUrl(longUrl);
    return createAutoIncShortUrl(longUrl);
}
