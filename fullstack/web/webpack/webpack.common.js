/* eslint-disable @typescript-eslint/no-var-requires */
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const HardSourceWebpackPlugin = require('hard-source-webpack-plugin');
const path = require('path');
const vendorPackage = ['axios', 'dplayer', 'uuid', 'vant', 'vue', 'vue-i18n', 'vue-router', 'vuex', 'element-ui', 'vue-cropper'];
const catchPackagesGrouped = () => {
    const result = {};

    vendorPackage.map(package => {
        result[package] = {
            test: module => module.resource && /\.js$/.test(module.resource) && module.resource.includes(path.join(__dirname, `../node_modules/${package}/`)),
            name: package,
            chunks: 'all',
            priority: -10
        };
    });

    return result;
};

module.exports = {
    target: 'web',
    plugins: [
        new CleanWebpackPlugin(),
        new HardSourceWebpackPlugin()
    ],
    optimization: {
        runtimeChunk: 'single',
        splitChunks: {
            chunks: 'async',
            minSize: 30000,
            maxSize: 0,
            minChunks: 1,
            maxAsyncRequests: 5,
            maxInitialRequests: 6,
            automaticNameDelimiter: '~',
            name: true,
            cacheGroups: {
                ...catchPackagesGrouped(),
                vendor: {
                    //除了vendorPackage包含的之外，其他的比较大的依赖包
                    test: module => {
                        let notIncludeVendor = false;
                        const conditionBase = module.resource && /\.js$/.test(module.resource);

                        vendorPackage.map(package => {
                            if (conditionBase && module.resource.includes(path.join(__dirname, `../node_modules/${package}/`))) {
                                notIncludeVendor = true;
                            }
                        });
                        return conditionBase && module.resource.includes(path.join(__dirname, '../node_modules/')) && !notIncludeVendor;
                    },
                    name: 'vendor',
                    chunks: 'all'
                },
                manifest: {
                    minChunks: Infinity
                },
                default: {
                    minChunks: 3,
                    priority: -20,
                    reuseExistingChunk: true
                }
            }
        },
        minimize: true
    },
    module: {
        rules: [{
            test: /\.(css|less)$/,
            use: [{
                loader: 'style-resources-loader',
                options: {
                    patterns: [path.resolve(__dirname, '../src/assets/style/global-var.less')]
                }
            }]
        }]
    }
};
