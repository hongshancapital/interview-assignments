import isUrl from '../isUrl';

describe('isUrl tests', () => {
  it('should be an invalid url', () => {
    expect(isUrl()).toEqual(false);
    expect(isUrl('')).toEqual(false);
    expect(isUrl('www.google.com')).toEqual(false);
    expect(isUrl('htp://www.google.com')).toEqual(false);
  });

  it('should be an valid url', () => {
    expect(isUrl('https://www.google.com')).toEqual(true);
    expect(isUrl('https://www.google.com?q=like')).toEqual(true);
  });
});
