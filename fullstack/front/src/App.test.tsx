import { isUrl } from './utils/regex';

test('regex', () => {
  expect(isUrl("aa")).toBeFalsy();
});
