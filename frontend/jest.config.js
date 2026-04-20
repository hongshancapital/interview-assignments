const config = {
  moduleFileExtensions: ['ts', 'tsx', 'js', 'jsx', 'json', 'md'],
  moduleNameMapper: {
    "\\.(css|scss)$": "identity-obj-proxy"
  },
  collectCoverageFrom: [
    'src/components/**/*.{ts,tsx}',
    '!src/tests/**'
  ]
};

module.exports = config;
