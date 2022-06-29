module.exports = {
  apps: [
    {
      name: 'nestjs-shorturl-server',
      script: 'node dist/src/main.js',
      env: {
        NODE_ENV: 'production'
      }
    }
  ],

  deploy: {
    production: {
      'user': '<SSH_USERNAME>',
      'host': '<SSH_HOSTMACHINE>',
      'ref': 'origin/main',
      'repo': '<GIT_REPOSITORY>',
      'path': '<DESTINATION_PATH>',
      'pre-deploy-local': '',
      'post-deploy':
        'npm install && pm2 reload ecosystem.config.js --env production',
      'pre-setup': ''
    }
  }
}
