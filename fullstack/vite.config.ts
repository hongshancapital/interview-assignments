import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react';

export default defineConfig({
    plugins: [react()],
    optimizeDeps: { include: ['react/jsx-dev-runtime'] },
    base: '/static/',
    rollupOptions: {
        input: {
            app: '.src/static/index.html',
        },
    },
    build: {
        minify: false,
    },
    root: "",

})
