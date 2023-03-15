import client from "../db/redis";
import connection from "../db/mysql";
import * as crypto from "crypto";
import Any = jasmine.Any;
import shortDomain from "../controllers/shortDomain";

const charSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

function dTo62(num: number) {
    let res = '';
    while (num) {
        let mod = num % 62;
        res = charSet[mod] + res;
        num = Math.floor(num / 62);
    }
    return res;
}

export const createShortDomain = async (domain: string) => {
    return new Promise((resolve) => {
        // const md5 = crypto.createHash("md5");
        // const sha1 = crypto.createHash("sha1");
        // md5.update(url);
        // let hash_md5 = md5.digest("hex");
        // sha1.update(url);
        // let hash_sha1 = sha1.digest("hex");

        const sql = "select * from domain_exchange as de where de.domain=?";
        const sqlParams = [domain];
        connection.query(sql, sqlParams, (err: Error, results: Array<any>) => {
            if (err) throw err;
            if (results.length == 0) {
                let insertSql = 'insert into domain_exchange set domain=?';
                let insertParams = [domain]
                connection.query(insertSql, insertParams, function (err: Error, results: any) {
                    if (err) throw err;
                    let key = dTo62(results.insertId);
                    client.set(key,domain);
                    resolve(key);
                });
            } else {
                let key = dTo62(results[0].id);
                client.set(key, domain);
                resolve(key);
            }
        });
    });
}

export const getDomain = async (shortDomain: string) => {
    //return await client.keys('*');
    return await client.get(shortDomain);
}