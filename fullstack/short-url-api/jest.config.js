module.exports = {
  testEnvironment: 'node',
  coverageDirectory: "./coverage",
  collectCoverageFrom: [
    "src/**/*.{js,ts}",
    "!src/app.{js,ts}",
    "!src/index.{js,ts}",
    "!src/schemas/index.{js,ts}"
  ],
  coverageThreshold: {
    global: {
      statements: 90,
      branches: 90,
      functions: 90,
      lines: 90,
    }
  },
  setupFiles: ["dotenv/config"],
  modulePaths: ['.'],
  moduleDirectories: ["node_modules", "src"],
  testMatch: ["**/?(*.)+(spec|test).[jt]s"],
  transformIgnorePatterns: ['<rootDir>/node_modules'],
  moduleFileExtensions: ['js', 'ts'],
};
