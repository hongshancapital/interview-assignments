/**
 * 16.8+ 版本react，使用render时会报警告，此处解决方案参考官方文档
 * https://www.npmjs.com/package/@testing-library/react
 * 搜索 act( 可查看官方解决方案
 */
const originalError = console.error;

beforeAll(() => {
  console.error = (...args) => {
    if (/Warning.*not wrapped in act/.test(args[0])) {
      return;
    }
    originalError.call(console, ...args);
  };
});

afterAll(() => {
  console.error = originalError;
});

export {};