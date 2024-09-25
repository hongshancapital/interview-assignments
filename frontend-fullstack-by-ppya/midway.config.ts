import react from '@vitejs/plugin-react';
import { defineConfig } from '@midwayjs/hooks-kit';
import path from 'path';

export default defineConfig({
  vite: {
    resolve: {
      alias: {
          "@": path.resolve(__dirname, "src"),
      },
    },
    plugins: [react()],
    extensions: [".js", ".ts", ".tsx", ".jsx"],
  },
});