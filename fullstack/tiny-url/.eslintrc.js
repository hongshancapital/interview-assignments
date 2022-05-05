module.exports = {
    parser: '@typescript-eslint/parser',
    extends: ['plugin:prettier/recommended'],
    parserOptions: {
        ecmaVersion: 2021,
        sourceType: 'module',
        ecmaFeatures: {
            jsx: true,
        },
    },
    env: {
        browser: true,
        node: true,
    },
};
