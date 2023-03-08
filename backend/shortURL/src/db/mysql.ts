const mysql = require('mysql')

const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'wx',
    port: '3306',
    database: 'test'
}) //配置获取略

connection.connect();

export default connection