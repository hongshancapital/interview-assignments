const { pathsToModuleNameMapper } = require('ts-jest')
const fs = require('fs');
const json5 = require('json5');
const { compilerOptions } = json5.parse(fs.readFileSync('./tsconfig.json'))

/** @type {import('ts-jest').JestConfigWithTsJest} */
module.exports = {
  preset: 'ts-jest',
  globals: {
    'ts-jest': {
      compiler: 'ts-patch/compiler'
    }
  },
  testEnvironment: 'node',
  
};