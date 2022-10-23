module.exports = {
  env: {
    es6: true,
  },
  extends: [
    "eslint:recommended",
    "plugin:@typescript-eslint/eslint-recommended",
    "prettier/@typescript-eslint",
    "plugin:prettier/recommended",
  ],
  globals: {
    Atomics: "readonly",
    SharedArrayBuffer: "readonly",
  },
  parser: "@typescript-eslint/parser",
  parserOptions: {
    ecmaVersion: 2020,
    sourceType: "module",
    project: "./tsconfig.eslint.json"
  },
  plugins: ["@typescript-eslint", "prettier"],
  rules: {
    "linebreak-style": ["error", "unix"],
    semi: ["error", "always"],
    eqeqeq: "error",
    curly: "error",
    "prefer-destructuring": "error",
    // Comment
    "spaced-comment": "error",
    "multiline-comment-style": "error",
    // String
    quotes: [
      "error",
      "single",
      { avoidEscape: true, allowTemplateLiterals: false },
    ],
    "prefer-template": "error",
    // Empty Line
    "no-multiple-empty-lines": [
      "error",
      {
        max: 1,
      },
    ],
    "padding-line-between-statements": [
      "error",
      {
        blankLine: "always",
        prev: ["var", "const", "let", "class"],
        next: "*",
      },
      {
        blankLine: "any",
        prev: ["const", "let", "var"],
        next: ["const", "let", "var"],
      },
      { blankLine: "always", prev: "*", next: "export" },
    ],
    "padded-blocks": ["error", "never"],
    // Function
    "arrow-body-style": "error",
    "prefer-arrow-callback": "error",
    "no-param-reassign": [
      "error",
      {
        props: true,
      },
    ],
    "no-loop-func": "error",
    "no-await-in-loop": "error",
    "no-restricted-syntax": [
      "error",
      "WithStatement",
      "DoWhileStatement",
      "ForStatement",
      "ForInStatement",
      {
        selector: "CallExpression[callee.name='require']",
        message: "require is not recommended, use import instead.",
      },
    ],
    // Spacing
    "object-curly-spacing": ["error", "always"],
    "key-spacing": [
      "error",
      {
        afterColon: true,
      },
    ],
    "keyword-spacing": "error",
    "no-trailing-spaces": "error",
    "no-multi-spaces": "error",
    "space-infix-ops": "error",
    "space-in-parens": "error",
    "space-before-function-paren": [
      "error",
      {
        anonymous: "always",
        named: "never",
        asyncArrow: "always",
      },
    ],
    "space-before-blocks": "error",
    // Tenary
    "no-unneeded-ternary": "error",
    "no-nested-ternary": "error",
    // TypeScript
    "@typescript-eslint/no-unused-vars": [
      "error",
      {
        vars: "all",
        args: "after-used",
        ignoreRestSiblings: false,
      },
    ],
    "@typescript-eslint/no-explicit-any": "error",
    "@typescript-eslint/no-unnecessary-type-assertion": "error",
  },
};
