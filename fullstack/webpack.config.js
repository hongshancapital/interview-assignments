/** @format */

const path = require('path');

module.exports = {
  entry: './src/server.ts',
  target: 'node',
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: [
          {
            loader: 'babel-loader',
            options: {
              presets: ['@babel/preset-env'],
            },
          },
          {
            loader: 'ts-loader',
          },
        ],
        exclude: /node_modules/,
      },
    ],
  },
  resolve: {
    extensions: ['.tsx', '.ts', '.js'],
  },
  output: {
    filename: 'bundle.js',
    libraryTarget: 'commonjs',
    path: path.resolve(__dirname, 'dist'),
  },
  externals: [/^(?!\.|\/).+/i],
};
