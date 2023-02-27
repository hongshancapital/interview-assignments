/* config-overrides.js */
const path = require('path');
const PKG = require("./package.json");
const {
  override,
  addWebpackAlias,
} = require('customize-cra');

if (process.env.NODE_ENV === "production") {
  process.env.GENERATE_SOURCEMAP = "false";
}

// 打包配置
module.exports = override(
  //别名配置
  addWebpackAlias({
    "@": path.join(__dirname, "./src"),
  }),
  (config) => {
    // 配置打包目录输出到dist/PKG.name
    const paths = require("react-scripts/config/paths");
    paths.appBuild = path.join(path.dirname(paths.appBuild), `dist/${PKG.name}`)
    config.output.path = paths.appBuild

    // 去掉打包生产map文件
    // config.devtool = config.mode === 'development' ? 'cheap-module-source-map' : false;
    if (process.env.NODE_ENV === "production") {
      config.devtool = false;
    }

    return config
  }
)




