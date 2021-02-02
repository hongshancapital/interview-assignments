module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'node',
  collectCoverage:true,
  coverageDirectory:".coverage",
  modulePathIgnorePatterns:[
      "<rootDir>/dist",
  ]
};
