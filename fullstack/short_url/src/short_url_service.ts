import { getDbConnection, query } from './db';
import { md5Hash, numberToShortString, shortStringToNumber } from './util';

const retryLimit = 3;
export const getShortUrl = async (url: string): Promise<string> => {
    const trimedUrl = url.trim();
    const connection = await getDbConnection();
    try{
        let saltedUrl = trimedUrl;
        let tryTimes = 0;
        while(true) {
            tryTimes ++;
            if(tryTimes > retryLimit) {
                throw 'Generate short url failed, too many confliction.';
            }
            const hashcode = md5Hash(saltedUrl);
            const res:{id: number, url: string}[] = await query(connection, `SELECT id, url FROM shorturl WHERE hashcode=?`, [hashcode]);
            if (res && res.length) {
                if (res[0].url === trimedUrl) {
                    return numberToShortString(res[0].id);
                } else {
                    saltedUrl+=' ';
                    continue; // add salt and try again.
                }
            } else {
                const res1 = await query(connection, `INSERT INTO shorturl SET ?`, [{hashcode, url: trimedUrl}]);
                return numberToShortString(res1.insertId);
            }
        }
    } finally {
        connection.release();
    }
};

export const getOrginalUrl = async (shortUrl: string) => {
    const id = shortStringToNumber(shortUrl.trim());
    const connection = await getDbConnection();
    try{
        const res:{url: string}[] = await query(connection, `select url from shorturl where id=?`, [id]);
        if (res && res.length) {
            return res[0].url;
        }else {
            return '';
        }
    } finally {
        connection.release();
    }
};