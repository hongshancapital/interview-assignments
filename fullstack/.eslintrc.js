module.exports = {
    env: {
      es6: true,
    },
    extends: [
      "eslint:recommended",
      "plugin:@typescript-eslint/eslint-recommended",
      "plugin:import/errors",
      "plugin:import/typescript",
      "prettier",
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
    },
    plugins: ["@typescript-eslint", "prettier", "import"],
    rules: {
      // Opinionated
      "import/no-named-as-default-member": "off",
      "linebreak-style": ["error", "unix"],
      semi: ["error", "always"],
      eqeqeq: "error",
      curly: "error",
      "prefer-destructuring": "error",
      "no-unused-expressions": "error",
      // Removing Redundancy
      "no-extra-bind": "error",
      "no-extra-label": "error",
      "no-useless-call": "error",
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
        {
          selector:
            "CallExpression[callee.type='MemberExpression'] MemberExpression[property.name=/^(copyWithin|fill|pop|push|reverse|shift|sort|splice|unshift)$/]",
          message: "DO NOT CALL MUTATING FUNCTION, THANKS.",
        },
        {
          selector:
            "JSXOpeningElement[name.name='img'] JSXAttribute[name.name='src'][value.expression.type!=/^(Identifier|CallExpression)$/]",
          message: "Import the source file first.",
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
      "space-before-blocks": "error",
      // Tenary
      "no-unneeded-ternary": "error",
      "no-nested-ternary": "error",
      // Assign
      "no-func-assign": "error",
      "no-class-assign": "error",
      // TypeScript
      "no-unused-vars": "off",
      "@typescript-eslint/no-unused-vars": [
        "error",
        {
          vars: "all",
          args: "after-used",
          ignoreRestSiblings: false,
          varsIgnorePattern: "_",
          argsIgnorePattern: "^_",
        },
      ],
      "@typescript-eslint/no-explicit-any": "error",
      "@typescript-eslint/no-unnecessary-type-assertion": "error",
      "@typescript-eslint/naming-convention": [
        "error",
        {
          selector: "variable",
          format: ["camelCase", "UPPER_CASE"],
        },
      ],
  
      // Import
      "import/order": [
        "error",
        {
          "newlines-between": "always",
          pathGroups: [
            {
              pattern: "@/**",
              group: "internal",
            },
          ],
          groups: [
            "builtin",
            "external",
            "type",
            "internal",
            ["parent", "sibling", "index"],
            "object",
          ],
        },
      ],
      "import/no-dynamic-require": "error",
      "import/no-cycle": "error", // this rule is comparatively computationally expensive, but it's good to have
      "import/no-useless-path-segments": "error",
      "import/no-unused-modules": [1, { unusedExports: true }],
      "import/export": "error",
      "import/no-mutable-exports": "error",
      "prefer-destructuring": "error",
      "prettier/prettier": "error",
      "no-restricted-imports": ["error", { paths: ["console"] }],
      // do not use global vars (such as 'status')
      "no-restricted-globals": [
        "error",
        {
          name: "status",
          message: "Use local parameter [status] instead.",
        },
        {
          name: "name",
          message: "Use local parameter [name] instead.",
        },
      ],
    },
    settings: {
      "import/resolver": {
        typescript: {},
      },
    },
  };