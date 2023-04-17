import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import path from 'path'
// https://vitejs.dev/config/
export default defineConfig({
    plugins: [react()],
    resolve:{
      //设置路径别名
      alias: {
        '@': path.resolve(__dirname, './src'),
        '@com': path.resolve(__dirname, './src/components'),
      },
    },
    server: {
      host: '0.0.0.0',//使用当前的IP地址，没有这个就是以localhost作为本地地址
      port: 5173, //端口号为5173
      open: false, //是否在默认浏览器中自动打开该地址
      proxy: { //使用代理
        '/api': { //当有 /api开头的地址是，代理到target地址
          target: 'https://api.***.com/', // 需要跨域代理的本地路径
          changeOrigin: true, //是否改变请求源头
        }
      }
    }
})
