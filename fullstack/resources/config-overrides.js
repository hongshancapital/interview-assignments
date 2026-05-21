const path = require('path')
const { fixBabelImports, override, addWebpackAlias } = require('customize-cra');
const CompressionWebpackPlugin = require('compression-webpack-plugin');

function resolve (dir) {
  return path.join(__dirname, '.', dir)
}

const addCustomize = () => config => {
  if (process.env.NODE_ENV === 'production') {
    config.devtool = false;
    // config.output.path = resolve('../server/public');
    config.plugins.push(
      new CompressionWebpackPlugin({
        test: /\.js$|\.css$/,
        threshold: 1024,
      }),
    )
  }
  return config;
}

module.exports = {
  webpack: override(
    fixBabelImports('import', {        
      libraryName: 'antd',        
      libraryDirectory: 'es',       
      style: 'css'
    }),
    addWebpackAlias({        
      '@components': resolve('src/components'),        
      '@utils': resolve('src/utils')  
    }),
    addCustomize()
  )
};
