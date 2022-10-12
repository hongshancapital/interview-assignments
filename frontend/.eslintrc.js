module.exports = {
  root: true,
  parser: "@typescript-eslint/parser",
  env: {
    browser: true,
    es2021: true,
    commonjs: true,
  },
  globals: {
    process: "writable",
    __dirname: "readonly",
  },
  extends: [
    "eslint:recommended",
    "plugin:react/recommended",
    "plugin:react-hooks/recommended",
    "plugin:@typescript-eslint/recommended",
  ],
  parserOptions: {
    ecmaVersion: 13,
    sourceType: "module",
  },
  settings: {
    react: {
      createClass: "createReactClass", // Regex for Component Factory to use,
      pragma: "React", // Pragma to use, default to "React"
      fragment: "Fragment", // Fragment to use (may be a property of <pragma>), default to "Fragment"
      version: "detect", // React version. "detect" automatically picks the version you have installed.
      flowVersion: "0.53", // Flow version
    },
  },
  plugins: ["prettier", "react"],
  rules: {
    "prettier/prettier": [
      "error",
      { arrowParens: "avoid", singleQuote: false },
    ],
    "react/prop-types": 0,
  },
  ignorePatterns: ["dist", "node_modules"],
};
