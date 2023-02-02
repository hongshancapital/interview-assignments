module.exports = {
  preset: "ts-jest",
  testEnvironment: "node",
  collectCoverage: true,
  collectCoverageFrom: [
    '**/*.{js,ts}',
    '!**/node_modules/**',
    '!**/build/**',
    '!**/coverage/**',
    '!**/entities/**',
    '!*jest.config.js',
    
  ],
  coverageDirectory: 'coverage',
  coverageProvider: 'babel',
  coverageReporters: ["json", "lcov", "text", "clover"],
  forceCoverageMatch: ["**/*.t.js"]
};