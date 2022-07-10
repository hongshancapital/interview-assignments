const path = require('path');
const CracoEsbuildPlugin = require('craco-esbuild');
// 针对提升开发体验
const isDevelopment = process.env.NODE_ENV === 'development';

module.exports = {
  webpack: {
    alias: {
      '@': path.resolve(__dirname, 'src'),
    },
  },
  plugins: [isDevelopment && { plugin: CracoEsbuildPlugin }].filter(Boolean),
};
