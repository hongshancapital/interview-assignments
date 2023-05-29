const path = require('path');

module.exports = {
    entry: './src/frontend/index.tsx',
    module: {
        rules: [
            {
                test: /\.tsx?$/,
                use: 'ts-loader',
                exclude: /node_modules/,
            },
        ],
    },
    resolve: {
        extensions: ['.tsx', '.ts'],
    },
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, '../../dist/frontend'),
    },
};
