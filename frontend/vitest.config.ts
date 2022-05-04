import path from 'path'
import { defineConfig } from 'vitest/config'

export default defineConfig({
  alias: {
    '@lib': path.resolve(__dirname, './lib/src'),
  },
  test: {
    globals: true,
    environment: 'jsdom',
    setupFiles: './test/setup.ts',
  },
})
