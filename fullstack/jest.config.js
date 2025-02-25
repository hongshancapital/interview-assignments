module.exports = {
    roots: [
      "<rootDir>/src"
    ],
    testEnvironment: "node",
    testMatch: [
      "**/tests/**/*.+(ts|tsx|js)",
      "**/?(*.)+(spec|test).+(ts|tsx|js)"
    ],
    transform: {
      "^.+\\.(ts|tsx)$": "ts-jest"
    },
    collectCoverageFrom: [
      "**/*.{js,jsx,ts,tsx}",
      "!**/node_modules/**",
      "!**/{App,server}.ts",
      "!**/src/routes/**"
    ],
    globals: {
      "ts-jest": {
        tsconfig: "tsconfig.json",
      },
    },
  }
  