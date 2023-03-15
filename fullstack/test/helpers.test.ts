/** @format */

import { splitUrl } from '../src/helpers';

test('split url', () => {
  const url = 'https://www.baidu.com/123456?test=789';
  expect(splitUrl(url).toString()).toBe(['https://www.baidu.com/', '123456?test=789'].toString());
});
