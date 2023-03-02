const path = require('path')
const nodeExternals = require('webpack-node-externals')

module.exports = {
  entry: './index.ts',
  target: 'node',
  output: {
    path: path.resolve(__dirname, './dist'),
    chunkFilename: '[id].js'
  },
  mode: 'development',
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: [
          { loader: 'babel-loader' },
          {
            loader: 'ts-loader',
            options: {
              transpileOnly: true,
              configFile: path.resolve(__dirname, './tsconfig.json')
            }
          }
        ],
        exclude: /node_modules/
      }
    ]
  },
  optimization: {
    splitChunks: {
      cacheGroups: {
        default: {
          name: 'common',
          chunks: 'initial'
        }
        // vendors: {  //拆分第三方库（通过npm|yarn安装的库）
        //   test: /[\\/]node_modules[\\/]/,
        //     name: 'vendor',
        //     chunks: 'initial',
        //     priority: -10
        // }
      }
    }
  },
  externalsPresets: { node: true },
  externalsType: 'commonjs',
  externals: [nodeExternals()],
  resolve: {
    extensions: ['.tsx', '.ts', '.js']
  },
  watch: true
}
