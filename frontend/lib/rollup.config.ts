import { defineConfig } from 'rollup'
import typescript from '@rollup/plugin-typescript'
import dts from 'rollup-plugin-dts'
import postcss from 'rollup-plugin-postcss'

export default defineConfig([
  {
    input: 'src/index.tsx',
    output: [
      {
        file: 'dist/index.mjs',
        format: 'es',
      },
      {
        file: 'dist/index.cjs',
        format: 'cjs',
        exports: 'auto',
      },
    ],
    plugins: [
      postcss({
        extract: true,
      }),
      typescript({
        tsconfig: './tsconfig.json',
      }),
    ],
  },
  {
    input: 'src/index.tsx',
    output: [{ file: 'dist/index.d.ts', format: 'es' }],
    plugins: [
      typescript({
        tsconfig: './tsconfig.json',
      }),
      dts(),
    ],
    external: [/\.scss$/],
  },
])
