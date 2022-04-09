/** @type {import('next').NextConfig} */
const path = require('path');

const NEXT_PUBLIC_ENVIRONMENT = process.env.NEXT_PUBLIC_ENVIRONMENT
const NODE_ENV = process.env.NODE_ENV
require('dotenv').config({
  path: `./.env.${NODE_ENV}`,
})

const env = {}

Object.keys(process.env).forEach((key) => {
  if (key.startsWith('NEXT_PUBLIC_')) {
    env[key] = process.env[key]
  }
})


module.exports = {
  env,
  reactStrictMode: true,
  sassOptions: {
    includePaths: [path.join(__dirname, 'styles')],
  },
  async redirects() {
    return [
    ]
  },
  images: {
    domains: [
      'url.co',
      'at.alicdn.com', 
    ],
  },
  distDir: `.${NEXT_PUBLIC_ENVIRONMENT}`,
  // exportPathMap: async function (defaultPathMap, { dev, dir, outDir, distDir, buildId }) {
  //   return {
  //     '/': { page: '/index' },
  //   }
  // },
};
