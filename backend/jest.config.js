module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'node',
  bail: 1,
  verbose: true,
  transform: {
    '^.+\\js$': 'babel-jest',
    "^.+\\.(t|j)sx?$": "ts-jest"
  },
  moduleFileExtensions: ['vue', 'js', 'json', 'jsx', 'ts', 'tsx', 'node'],
  roots: [
    "<rootDir>/test"
  ],
  testRegex: 'test/(.+)\\.test\\.(jsx?|tsx?)$',
};