const request = require('supertest');
import {
    validateUrl,
    isLongDomain,
    isShortDomain
} from '../../utilites/util';

const baseURL = 'http://localhost:3003';

describe('POST /api', () => {
    const longUrl = {
    origUrl: "https://www.makeuseof.com/express-apis-jest-test/"
    }
      it("long url return short url", async () => {
        const response = await request(baseURL).post("/api").send(longUrl);
        expect(response.statusCode?.toString()[0]).toBe('2');
        expect(isShortDomain(response.text)).toBeTruthy();
    });

    const shortUrl1 = {
        origUrl: "https://shorturl.at/k0qDocVp"
    }
    it("short url in database return long url", async () => {
        const response = await request(baseURL).post("/api").send(shortUrl1);
        expect(response.statusCode?.toString()[0]).toBe('2');
        expect(isLongDomain(response.text)).toBeTruthy();
    })

    const shortUrl2 = {
        origUrl: "https://shorturl.at/0000444"
    }
    it("short url not in database return short url", async () => {
        const response = await request(baseURL).post("/api").send(shortUrl2);
        expect(response.statusCode?.toString()[0]).toBe('2');
        expect(isShortDomain(response.text)).toBeTruthy();
    })

    const invalidUrl = {
        origUrl: "https 1234567890"
    }
    it("invalid url return invalid url", async () => {
        const response = await request(baseURL).post("/api").send(invalidUrl);
        expect(response.statusCode?.toString()[0]).toBe('2');
        expect(validateUrl(response.text)).toBeFalsy();
    })
})