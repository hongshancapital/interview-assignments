module.exports = {
  transform: {
    '^.+\\.(j|t)sx?$': 'ts-jest',
  },
  testTimeout: 15000,
  testRegex: '(/tests/.*(test|spec|e2e))\\.(jsx?|tsx?)$',
  moduleDirectories: ['node_modules', 'src'],
  moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'node'],
  collectCoverageFrom: [
    "./src/**/*.ts"
  ],
  coverageReporters: ['text', 'text-summary', 'lcov', 'clover'],
  clearMocks: true,
  bail: 1,
  roots: ['<rootDir>/tests/'],
}