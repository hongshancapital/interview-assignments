/** @type {import('ts-jest/dist/types').InitialOptionsTsJest} */
module.exports = {
  preset: "ts-jest",
  testEnvironment: "node",
  testRegex: "(.(test|spec))\\.(ts|tsx)$",
  // transformIgnorePatterns: [`/node_modules/(?!${esModules})`],
  moduleNameMapper: {
    "^nanoid(/(.*)|$)": "nanoid$1",
  },
};
