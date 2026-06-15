module.exports = {
    preset: "ts-jest",
    testEnvironment: "node",
    testRegex: "(.(test|spec))\\.(ts|tsx)$",
    moduleNameMapper: {
        "^nanoid(/(.*)|$)": "nanoid$1",
    }
};