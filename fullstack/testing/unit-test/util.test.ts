import {
    validateUrl,
    isLongDomain,
    isShortDomain
} from '../../utilites/util';

test('valid url', () => {
    expect(validateUrl('https://www.seismic.com')).toBeTruthy();
    expect(validateUrl('https://jestjs.io/docs/using-matchers')).toBeTruthy();
    expect(validateUrl('abc')).toBeFalsy();
    expect(validateUrl('http://111')).toBeFalsy();
})

test('long domain', () => {
    expect(isLongDomain('https://nodejs.org/api/url.html#urlhost')).toBeTruthy();
    expect(isLongDomain('https://jestjs.io/docs/using-matchers')).toBeTruthy();
    expect(isLongDomain('https://short.at/a182')).toBeFalsy();
    expect(isLongDomain('https://www.badi.com')).toBeFalsy();
})

test('short domain', () => {
    expect(isShortDomain('https://shorturl.at/k0qDocVp')).toBeTruthy();
    expect(isShortDomain('https://nodejs.org/api/url.html#urlhost')).toBeFalsy();
    expect(isShortDomain('https://jestjs.io/docs/using-matchers')).toBeFalsy();
    expect(isShortDomain('https://short.at/a182')).toBeTruthy();
    expect(isShortDomain('https://www.badi.com')).toBeTruthy();
})