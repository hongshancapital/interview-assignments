module.exports = {
  apps : [{
    script: 'build/main.js',
    merge_logs: true,
    max_restarts: 20,
    instances: 5,
    exec_mode  : "cluster"
  }]
};
