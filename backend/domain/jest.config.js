module.exports = {
  moduleFileExtensions: ['json', 'ts', 'js'],
  preset: 'ts-jest',
  // 指定测试文件的后缀名为 .test.ts
  testMatch: [`**/test/**/*.{test,spec}.ts`],
  // 指定使用 ts-jest 来解析 TypeScript 代码
  transform: {
    "^.+\\.(t)s$": "ts-jest"
  },
  // 指定需要忽略的目录，如 node_modules
  modulePathIgnorePatterns: ["./node_modules"],
  coverageReporters: ['html', 'text-summary', 'text', 'lcov'],
  coverageDirectory: 'coverage/base',
  collectCoverageFrom: ['src/**'],
  coverageProvider: 'v8',
  // 指定需要运行的环境
  testEnvironment: "node",
  // 指定在控制台输出哪些信息
  verbose: true,
  reporters: [
    'default',
    'jest-summary-reporter'
  ],
};
  