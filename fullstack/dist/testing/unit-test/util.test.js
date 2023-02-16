"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const util_1 = require("../../utilites/util");
test('valid url', () => {
    expect((0, util_1.validateUrl)('https://www.seismic.com')).toBeTruthy();
    expect((0, util_1.validateUrl)('https://jestjs.io/docs/using-matchers')).toBeTruthy();
    expect((0, util_1.validateUrl)('abc')).toBeFalsy();
    expect((0, util_1.validateUrl)('http://111')).toBeFalsy();
});
test('long domain', () => {
    expect((0, util_1.isLongDomain)('https://nodejs.org/api/url.html#urlhost')).toBeTruthy();
    expect((0, util_1.isLongDomain)('https://jestjs.io/docs/using-matchers')).toBeTruthy();
    expect((0, util_1.isLongDomain)('https://short.at/a182')).toBeFalsy();
    expect((0, util_1.isLongDomain)('https://www.badi.com')).toBeFalsy();
});
test('short domain', () => {
    expect((0, util_1.isShortDomain)('https://shorturl.at/k0qDocVp')).toBeTruthy();
    expect((0, util_1.isShortDomain)('https://nodejs.org/api/url.html#urlhost')).toBeFalsy();
    expect((0, util_1.isShortDomain)('https://jestjs.io/docs/using-matchers')).toBeFalsy();
    expect((0, util_1.isShortDomain)('https://short.at/a182')).toBeTruthy();
    expect((0, util_1.isShortDomain)('https://www.badi.com')).toBeTruthy();
});
