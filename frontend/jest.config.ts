/*
 * For a detailed explanation regarding each configuration property and type check, visit:
 * https://jestjs.io/docs/configuration
 */

export default {
    "roots": [
        "<rootDir>/src"
    ],
    "collectCoverageFrom": [
        "src/**/*.{js,jsx,ts,tsx}",
        "!src/**/*.d.ts"
    ],
    "setupFiles": [
        "react-app-polyfill/jsdom"
    ],
    "setupFilesAfterEnv": [
        "./node_modules/jest-enzyme/lib/index.js",
        "<rootDir>/src/setupTests.ts"
    ],
    "testMatch": [
        "<rootDir>/src/**/__tests__/**/*.{js,jsx,ts,tsx}",
        "<rootDir>/src/**/*.{spec,test}.{js,jsx,ts,tsx}"
    ],
    "testEnvironment": "jsdom",
    "transform": {
        "^.+\\.(js|jsx|mjs|cjs|ts|tsx)$": "<rootDir>/config/jest/babelTransform.js",
        "^.+\\.css$": "<rootDir>/config/jest/cssTransform.js",
        "^(?!.*\\.(js|jsx|mjs|cjs|ts|tsx|css|json)$)": "<rootDir>/config/jest/fileTransform.js"
    },
    "transformIgnorePatterns": [
        "[/\\\\]node_modules[/\\\\].+\\.(js|jsx|mjs|cjs|ts|tsx)$",
        "^.+\\.module\\.(css|sass|scss)$"
    ],
    "modulePaths": [],
    "moduleNameMapper": {
        "^react-native$": "react-native-web",
        "^.+\\.module\\.(css|sass|scss)$": "identity-obj-proxy"
    },
    "moduleFileExtensions": [
        "web.js",
        "js",
        "web.ts",
        "ts",
        "web.tsx",
        "tsx",
        "json",
        "web.jsx",
        "jsx",
        "node"
    ],
    "watchPlugins": [
        "jest-watch-typeahead/filename",
        "jest-watch-typeahead/testname"
    ],
    "resetMocks": true
};