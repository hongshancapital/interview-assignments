/*
 * For a detailed explanation regarding each configuration property, visit:
 * https://jestjs.io/docs/configuration
 */

module.exports = {
    preset: 'ts-jest',
    globals: {
      'ts-jest': {
        tsconfig: 'tsconfig.json',
      },
    },
    moduleDirectories: [
      "node_modules",
      "src"
    ],
    moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx'],
    setupFilesAfterEnv: ['@testing-library/jest-dom'],
    testEnvironment: "jsdom",
    moduleNameMapper: {
      "\\.(css|less|scss|sss|styl)$": "jest-css-modules",
      "@/(.*)": "<rootDir>/src/$1",
    },
    testMatch: [
    "**/__tests__/**/*.[jt]s?(x)",
    "**/+(*.)+(spec|test).[tj]s?(x)"
    ],
    testPathIgnorePatterns: [
    "/node_modules/"
    ],
    transformIgnorePatterns: ['[/\\\\]node_modules[/\\\\].+\\.(js|jsx)$'],
};