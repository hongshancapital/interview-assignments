/**
 * @type {import("eslint").Linter.Config}
 */
module.exports = {
  extends: [
    "eslint:recommended",
    "plugin:@typescript-eslint/recommended",
    "plugin:react/recommended",
    "plugin:prettier/recommended",
  ],
  parser: "@typescript-eslint/parser",
  parserOptions: {
    ecmaFeatures: { jsx: true },
    sourceType: "module",
  },
  ignorePatterns: ["*rc.*js", "*.config.*js"],
  plugins: ["@typescript-eslint", "import", "react", "prettier"],
  root: true,
  rules: {
    "import/extensions": [2, "ignorePackages"],
  },
  settings: {
    react: {
      version: "detect",
    }
  },
};
