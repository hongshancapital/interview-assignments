module.exports = {
    apps: [
        {
            name: 'shorten',
            script: './dist/www.js',
            instances: 2,
            autorestart: true,
            watch: false,
            max_memory_restart: '1G',
            exec_mode: "cluster",
            log_date_format: 'YYYY-MM-DD HH:mm Z',
            merge_logs: true,
            error_file: '/opt/log/err.log',
            out_file: '/opt/log/access_out.log',
            env: {
                NODE_ENV: 'development',
                PORT: 3000,
                MONGODB_URI: 'mongodb://127.0.0.1:27017/short'
            },
            env_production: {
                NODE_ENV: 'production',
                PORT: 3002,
                MONGODB_URI: 'mongodb://127.0.0.1:27017/short'
            }
        },
    ]
};
