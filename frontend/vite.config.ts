import { defineConfig } from "vite"
import react from "@vitejs/plugin-react"
import path from "path"

const resolvePath = (value: string) => path.resolve(__dirname, value)

// https://vitejs.dev/config/
export default defineConfig({
  base: "./",
  resolve: {
    alias: {
      "@": resolvePath("src"),
    },
  },
  plugins: [react()],
})
