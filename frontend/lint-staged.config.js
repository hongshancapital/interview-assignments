const base = ['prettier --write', 'git add'];

module.exports = {
  'src/**/*.{ts,tsx,js}': ['eslint --fix', ...base],
  '*.{json,md}': base,
};
