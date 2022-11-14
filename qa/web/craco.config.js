const path = require('node:path');

/**
 * @type {import('@craco/types').CracoConfig}
 */
module.exports = {
  webpack: {
    alias: {
      '@': path.resolve('src'),
    },
  },
  jest: {
    configure: (jestConfig) => {
      jestConfig.moduleNameMapper = {
        ...jestConfig.moduleNameMapper,
        '^@/(.*)$': '<rootDir>/src/$1',
        axios: 'axios/dist/node/axios.cjs',
      };

      return jestConfig;
    },
  },
};
