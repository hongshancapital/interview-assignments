module.exports = {
    plugins: [
        '@babel/plugin-proposal-class-properties',
        "babel-plugin-styled-components",
        "@babel/plugin-syntax-dynamic-import"
    ],
    presets: [
        '@babel/preset-env',
        '@babel/preset-react',
    ],
    env: {
        development: {
            plugins: [
            	// 加上这个plugin可以免去引入antd全局样式这一步
                ['import-separation', { libraryName: 'antd', libraryDirectory: 'es', style: true }],
                '@babel/plugin-transform-runtime',
                // react热更新所需要的babel
                'react-hot-loader/babel',
                "@babel/plugin-proposal-class-properties",
                ["babel-plugin-styled-components", { "displayName": true }],
                "@babel/plugin-syntax-dynamic-import"
            ],
            presets: ["@babel/preset-env", "@babel/preset-react"]
        },
        production: {
            plugins: [
                ['import-separation', { libraryName: 'antd', libraryDirectory: 'es', style: true }],
                '@babel/plugin-transform-runtime',
                'react-hot-loader/babel',
                "@babel/plugin-proposal-class-properties",
                "babel-plugin-styled-components",
                "@babel/plugin-syntax-dynamic-import"
            ],
            presets: ["@babel/preset-env", "@babel/preset-react"]
        },
        /**
         * test环境下不能配置import-separation @babel/plugin-transform-runtime react-hot-loader三种plugin
         * 否则测试会出错：SyntaxError: Cannot use import statement outside a module
         */
        test: {
            plugins: [
                "@babel/plugin-proposal-class-properties",
                ["babel-plugin-styled-components", { "displayName": true }],
                "@babel/plugin-syntax-dynamic-import"
            ],
            presets: ["@babel/preset-env", "@babel/preset-react"]
        },
    }
};
