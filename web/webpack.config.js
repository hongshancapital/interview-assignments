const { CleanWebpackPlugin } = require('clean-webpack-plugin');

const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const path = require('path');
const { ProgressPlugin } = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');

const CopyPlugin = require('copy-webpack-plugin');

const srcPath = path.resolve(__dirname, 'src');
const buildPath = path.resolve(__dirname, 'build');


const isProd = process.env.NODE_ENV === 'production';

module.exports = {
  entry: './src/index.tsx',
  stats: isProd ? 'normal' : 'minimal',
  devServer: {
    port: 3000,
    clientLogLevel: 'silent',
    open: true,
    historyApiFallback: true,
  },
  devtool: !isProd && 'source-map',
  mode: isProd ? 'production' : 'development',
  module: {
    rules: [
      {
        test: /\.s[ac]ss$/i,
        use: [
          isProd ? MiniCssExtractPlugin.loader : 'style-loader',
          {
            loader: '@teamsupercell/typings-for-css-modules-loader',
            options: {
              disableLocalsExport: true,
            },
          },
          {
            loader: 'css-loader',
            options: {
              modules: { localIdentName: isProd ? '[hash:base64]' : '[path][name]__[local]' },
              sourceMap: !isProd,
            },
          },
          { loader: 'postcss-loader', options: { sourceMap: !isProd } },
          { loader: 'sass-loader', options: { sourceMap: !isProd } },
        ],
      },
      {
        test: /\.css$/i,
        use: [isProd ? MiniCssExtractPlugin.loader : 'style-loader', 'css-loader'],
      },
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/,
      },
      {
        test: /\.(png|svg|jpg|gif)$/,
        use: 'file-loader',
      },
    ],
  },
  resolve: {
    alias: {
      '@': srcPath,
    },
    extensions: ['.tsx', '.ts', '.js'],
  },
  output: {
    path: buildPath,
    publicPath: '/',
    filename: isProd ? '[name].[chunkhash].js' : '[name].js',
    chunkFilename: isProd ? '[name].[chunkhash].bundle.js' : '[name].bundle.js',
  },
  plugins: (isProd ? [new CleanWebpackPlugin()] : [new ProgressPlugin()]).concat([
    new HtmlWebpackPlugin({ template: 'src/index.html', publicPath: '/' }),
    new MiniCssExtractPlugin({
      filename: isProd ? '[name].[contenthash].css' : '[name].css',
      chunkFilename: isProd ? '[name].[contenthash].bundle.css' : '[name].bundle.css',
    }),
    new CopyPlugin({
      patterns: [{ from: 'public', to: buildPath }],
    }),
  ]),
};
