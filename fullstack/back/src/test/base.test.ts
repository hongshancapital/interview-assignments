import SnowFlake from '../util/snowflake';
import { string10to62 } from '../util/base';

describe('md5', () => {
    test("md5 encode twice equal", () => {
        expect(require('blueimp-md5')("longlongstr")).toBe(require('blueimp-md5')("longlongstr"));
    })
})

describe('snowflakeId', () => {
    test("SnowFlake ascorder", () => {
        const idWorker = new SnowFlake(1n, 1n);
        const test1 = idWorker.nextId();
        const test2 = idWorker.nextId();
        expect(test1 < test2);
    })
})

describe('string10to62', () => {
    test("string10to62 right", () => {
        expect(string10to62(10n)).toBe('a');
    })
    test("string10to62 right", () => {
        expect(string10to62(61n)).toBe('Z');
    })
})