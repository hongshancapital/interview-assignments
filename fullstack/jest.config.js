module.exports = {
    testEnvironment: 'node',
    transform: {
        "^.+\\.tsx?$": "ts-jest",
    },
    moduleFileExtensions: [
        "ts",
        "tsx",
        "js",
        "jsx",
        "json",
        "node",
    ],
    testRegex: '(/tests/.*|(\\.|/)(test|spec))\\.(ts|js)x?$',
    coverageDirectory: 'coverage',
    collectCoverageFrom: [
        '*.{ts,tsx,js,jsx}',
        '*.d.ts',
    ],
    collectCoverage: true
};