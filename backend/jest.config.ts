export default {
    transform: { "^.+\\.ts?$": "ts-jest" },
    clearMocks: true,
    collectCoverage: true,
    coverageDirectory: "coverage",
    coverageProvider: "v8",
    preset: 'ts-jest',
    testEnvironment: "node",
}