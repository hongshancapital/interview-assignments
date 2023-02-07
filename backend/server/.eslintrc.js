module.exports = {
    root: true,
    env: {
        browser: true,
        node: true,
        amd: true
    },
    // parser: "@babel/eslint-parser",
    parserOptions: {
        ecmaVersion: 2017,
        sourceType: 'module',
        project: "./tsconfig.json",
        tsconfigRootDir: __dirname,
    },
    // ignorePatterns: ['.eslintrc.js'],
    rules: {
        '@typescript-eslint/explicit-module-boundary-types': 'off',
        '@typescript-eslint/ban-types': 'warn',
        '@typescript-eslint/no-namespace': 'warn',
        'jsdoc/require-returns-description': 0,
        'jsdoc/require-param-description': 0,
        'keyword-spacing': ['error',
            {
                'after': true, 'before': true
            }
        ],
        'new-cap': 1,
        'no-shadow': ['off'],
        indent: ['error', 4],
        quotes: [
            'warn',
            'single',
            { avoidEscape: true, allowTemplateLiterals: true },
        ],
        semi: ['error', 'always'],
        'max-len': ['warn', {
            code: 100,
            ignoreUrls: true,
            ignoreComments: true,
            ignoreRegExpLiterals: true,
            ignoreTemplateLiterals: true,
        }],
        'linebreak-style': 'off',
        'require-jsdoc': 'off',
        'line-comment-position': 'off',
        // sequelize-auto模型中有先引用再定义class的情况
        'no-use-before-define': 'warn',
        // sequelize-auto模型中有转义
        'no-useless-escape': 'warn',
        '@typescript-eslint/no-this-alias': ['off'
        ],
        "max-lines-per-function": ["error", 250]
    }
};
