import { fixEasing } from './utils';

test('fix easing', () => {
  expect(fixEasing('linear')).toBe('linear');
  expect(fixEasing('ease')).toBe('ease');
  expect(fixEasing('ease-in')).toBe('ease-in');
  expect(fixEasing('ease-in-out')).toBe('ease-in-out');
  expect(fixEasing('ease-out')).toBe('ease-out');
  expect(fixEasing('cubic-bezier()')).toBe('linear');
  expect(fixEasing('cubic-bezier(0,0,0)')).toBe('linear');
  expect(fixEasing('cubic-bezier(0.1, 0.2, 0.3, 1.1)')).toBe('linear');
  expect(fixEasing('cubic-bezier(0.1, 0.2, 0.3, 0.4)')).toBe('cubic-bezier(0.1, 0.2, 0.3, 0.4)');
  expect(fixEasing('cubic-bezier(1, 1, 1, 1)')).toBe('cubic-bezier(1, 1, 1, 1)');
});