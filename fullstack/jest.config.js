/**
 * @type { import("@jest/types").Config.InitialProjectOptions }
 */
const browser = {
  displayName: "Browser",
  id: "browser",
  preset: "ts-jest/presets/default-esm",
  testEnvironment: "jsdom",
  moduleNameMapper: {
    "^(\\.{1,2}/.*)\\.js$": "$1",
  },
  testMatch:  ["<rootDir>/src/browser/**/*.test.{js,jsx,ts,tsx}"],
}

/**
 * @type { import("@jest/types").Config.InitialProjectOptions }
 */
const server = {
  displayName: "Server",
  id: "server",
  preset: "ts-jest/presets/default-esm",
  testEnvironment: "node",
  moduleNameMapper: {
    "^(\\.{1,2}/.*)\\.js$": "$1",
  },
  testMatch:  ["<rootDir>/src/server/**/*.test.{js,ts}"],
}

/**
 * @type { import("@jest/types").Config.InitialOptions }
 */
export default {
  collectCoverage: true,
  collectCoverageFrom: [
    "<rootDir>/src/**/*.{js,ts}",
    "!<rootDir>/src/**/*.test.{js,ts}"
  ],
  projects:[browser,server]
}