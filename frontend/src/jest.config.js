module.exports = {
    preset: 'ts-jest',
    testEnvironment: 'jsdom',
    moduleNameMapper: {
      '\\.(css|less|scss|sss|styl)$': '<rootDir>/node_modules/jest-css-modules',
    },
    setupFiles: [
    //   '<rootDir>/node_modules/web-animations-js/web-animations.min.js',
    //   '<rootDir>/node_modules/jest-canvas-mock/lib/index.js',
    //   '<rootDir>/node_modules/raf/polyfill.js',
      '<rootDir>/jest.setup.js'
    ],
  };
  