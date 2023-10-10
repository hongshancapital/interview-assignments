import { describe, test, expect, } from 'vitest';
import Util from '../src/util.js';

describe('Util unit test', () => {
    describe('getPath', () => {
        test('getPath normal', () => {
            const url = 'https://s.com/aq1';
            const path = Util.getPath(url);
            expect(path).toEqual('aq1');
        });
    
        test('getPath invalid host', () => {
            const url = 'https://s2.com/aq1';
            const path = Util.getPath(url);
            expect(path).toBe(null);
        });
    
        test('getPath Incorrect pattern', () => {
            const url = 'https://s.com/aQ1';
            const path = Util.getPath(url);
            expect(path).toBe(null);
        });
    
        test('getPath too long', () => {
            const url = 'https://s.com/loooooong';
            const path = Util.getPath(url);
            expect(path).toBe(null);
        });
    
        test('getPath no path provided', () => {
            const url = 'https://s.com/';
            const path = Util.getPath(url);
            expect(path).toBe(null);
        });
    });

    describe('calcPathToId', () => {
        test('calcPathToId normal', () => {
            const path = 'aq1';
            const id = Util.calcPathToId(path);
            expect(id).toEqual(13897);
        });

        test('calcPathToId extreme', () => {
            const path = 'zzzzzzzz';
            const id = Util.calcPathToId(path);
            expect(id).toEqual(2821109907455);
        });
    });

    describe('calcIdToPath', () => {
        test('calcIdToPath normal', () => {
            const id = 2821109907455;
            const path = Util.calcIdToPath(id);
            expect(path).toEqual('zzzzzzzz');
        });

        test('calcIdToPath invalid', () => {
            const id = -2821109907455;
            const path = Util.calcIdToPath(id);
            expect(path).toBe(null);
        });
    });

    describe('buildShortUrl', () => {
        test('buildShortUrl normal', () => {
            const path = 'aq1';
            const url = Util.buildShortUrl(path);
            expect(url).toEqual('https://s.com/aq1');
        });
    });
});
