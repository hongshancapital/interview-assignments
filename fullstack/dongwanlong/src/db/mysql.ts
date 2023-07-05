"use strict";
import mysql from 'mysql'

const pool = mysql.createPool({
    host     : "localhost",
    user     : "root",
    password : "123456",
    database : "short_domain",
    connectionLimit:100,
});

export function exec<T>(sql: string){
    return new Promise<T>(function(resolve, reject){
        pool.getConnection(function(err, conn){
            if(err)return reject(err);
            conn.query(sql,function(qerr, vals, fields){
                conn.release()
                if(qerr){
                    reject(qerr);
                }else{
                    resolve(vals);
                }
            });
        });
    });
};