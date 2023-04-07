import Db from "mysql2-async";
const mysql = new Db({
    host     : '127.0.0.1',
    user     : 'root',
    database : 'test'
});

const LONG_2_SHORT_URL_SQL = 'select short_url from t_short_url where long_url = ?';
const SHORT_2_LONG_URL_SQL = 'select long_url from t_short_url where short_url = ?';
const INSERT_SQL = 'insert into t_short_url values(?, ?)';

exports.long2ShortURL = async (longURL: string) => {
    const shortURL = await mysql.getval(LONG_2_SHORT_URL_SQL, [longURL]);
    return shortURL;
}

exports.short2LongURL = async (shortURL: string) => {
    const longURL = await mysql.getval(SHORT_2_LONG_URL_SQL, [shortURL]);
    return longURL;
}

exports.setURL = async (shortURL: string, longURL: string) => {
    const insertID = await mysql.insert(INSERT_SQL, [shortURL, longURL]);
    return insertID;
}