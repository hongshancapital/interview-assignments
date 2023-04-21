module.exports = {
    transform: {
      "^.+\\.(ts|tsx)$": "ts-jest",
    },
    testEnvironment: "node",
    testRegex: "/__tests__/.*\\.(test|spec)?\\.(ts|tsx)$",
    moduleFileExtensions: ["ts", "tsx", "js", "jsx", "json", "node"],
    transformIgnorePatterns: [
      "node_modules/(?!(nanoid)/)"
    ],
  };
  
