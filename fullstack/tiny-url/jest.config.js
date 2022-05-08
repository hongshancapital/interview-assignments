module.exports = {
    verbose: true,
    roots: ['<rootDir>/src/api'],
    preset: 'ts-jest',
    testEnvironment: 'node',
    testMatch: ['**/__tests__/**/*.+(ts|tsx|js)', '**/?(*.)+(spec|test).+(ts|tsx|js)'],
    transform: {
        '^.+\\.(ts|tsx)$': 'ts-jest',
    },
    collectCoverageFrom: [
        '**/*.{js,jsx,ts,tsx}',
        '!**/*.d.ts',
        '!**/node_modules/**',
        //'!**/routers/**',
        //'!**/models/**',
        '!**/services/db/{Mongoose,RedisCache}.ts',
        '!**/{ApiApp,server,index}.ts',
    ],
    globals: {
        'ts-jest': {
            tsconfig: 'tsconfig.json',
        },
    },
    setupFilesAfterEnv: ['<rootDir>/jest-preload.js'],
};
