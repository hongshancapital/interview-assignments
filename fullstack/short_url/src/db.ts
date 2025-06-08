import mysql from 'mysql';

const pool = mysql.createPool({
    connectionLimit: 1,
    host: '172.23.64.1',
    port: 3306,
    user: 'root',
    password: '123456',
    database: 'testExpress',
    timeout: 100,
});

export const getDbConnection = async (): Promise<mysql.PoolConnection> => {
    return new Promise((resove, reject) => {
        pool.getConnection((error, connection) => {
            if(error) {
                reject(error);
            } else {
                resove(connection);
            }
        });
    });
};

export const query = async (connection: mysql.PoolConnection, sql: string, values: any): Promise<any> => {
    return new Promise((resove, reject) => {
        connection.query(sql, values, ((error, res) => {
            if(error) {
                reject(error);
            } else {
                resove(res);
            }
        }));
    });
};
