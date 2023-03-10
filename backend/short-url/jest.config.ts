import type { Config } from 'jest';

const config: Config = {
    testEnvironment: 'node',
    coverageDirectory: './coverage',
    collectCoverageFrom: [
        'src/**/*.{js,ts}',
        '!src/app.{js,ts}',
        '!src/index.{js,ts}',
        '!src/config/*.{js,ts}',
        '!src/entities/index.{js,ts}'
    ],
    coverageThreshold: {
        global: {
            statements: 90,
            branches: 90,
            functions: 90,
            lines: 90
        }
    },
    modulePaths: ['.'],
    moduleNameMapper: {
        '@config': 'src/config'
    },
    moduleDirectories: ['node_modules', 'src'],
    modulePathIgnorePatterns: ['src/config'],
    testMatch: ['**/?(*.)+(spec|test).[jt]s'],
    transformIgnorePatterns: ['<rootDir>/node_modules', '<rootDir>/dist'],
    preset: 'ts-jest',
    transform: { '^.+\\.ts?$': 'ts-jest' },
    moduleFileExtensions: ['js', 'ts']
};

export default config;
