module.exports = {
  apps: [{
    name: 'iyoServer',
    script: 'dist/app.js',
    instances: 1,
    autorestart: true,
    watch: false,
    'ignore_watch': ['node_modules'],
    'source_map_support': false,
    'max_memory_restart': '1G',
    env: {
      NODE_ENV: 'production'
    }
  }]
}
