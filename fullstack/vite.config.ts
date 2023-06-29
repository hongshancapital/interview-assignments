import react from "@vitejs/plugin-react";
import { defineConfig } from "vite";

import path from "path";
import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

export default defineConfig({
  build: {
    outDir: `${__dirname}/dist/browser`,
    rollupOptions: {
      output: {
        manualChunks: {
          react: ["react", "react-dom"],
        },
      },
      treeshake: "safest",
    },
  },
  plugins: [react()],
  root: "./src/browser",
  server: {
    host: true,
    port: 8081,
  },
});
