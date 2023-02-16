import { v4 as uuidv4 } from 'uuid';

import Connection from '../config/db';
import { urlHanlder } from './interface';

export default class DBConnect {

    insertDatabase(longDomain: string, shortDomain: string, handleResult: urlHanlder) {
        const sql = `insert into urlTable  (ID, longDomain, shortDomain) values ('${uuidv4()}', '${longDomain}', '${shortDomain}');`;
        Connection.query(sql, function(err, data) {
            handleResult(err, data);
        })
    }

    findLongDomain(shortUrl: string, handleResult: urlHanlder) {
        const sql = `select * from urlTable where shortDomain = '${shortUrl}'`;
        Connection.query(sql, function(err, data) {
            console.dir(data)
            handleResult(err, data[0]?.longDomain);
        })
    }

    findShortDomain(longUrl: string, handleResult: urlHanlder) {
        const sql = `select * from urlTable where longDomain = '${longUrl}'`;
        Connection.query(sql, function(err, data) {
            handleResult(err, data[0]?.shortDomain);
        });
    }
}