import { defineConfig } from "vite";
import reactRefresh from "@vitejs/plugin-react-refresh";
import hooks from "@midwayjs/vite-plugin-hooks";
import path from "path";

/**
 * 相对路径转换为项目绝对路径
 * @param dir 目录信息
 * @returns
 */
const resolve = (dir: string) => {
  return path.join(__dirname, dir);
};

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [hooks(), reactRefresh()],
  resolve: {
    alias: {
      "@apis": resolve("./src/apis"),
    },
  },
});
