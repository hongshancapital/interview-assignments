import db from '../infrastructure/mysql';

/**
 * 存储短域名
 * @param shortUrl 短域名
 * @param originUrl 原始域名
 */
function saveShortUrl(shortUrl: string, originUrl: string): Promise<boolean> {
    const sql = 'INSERT INTO short_url.short_url SET ?';
    const curDateTime = new Date();
    return new Promise((resolve, reject)=>{
        db.pool.query(
            sql, 
            {short_url: shortUrl, origin_url: originUrl, create_time: curDateTime, update_time: curDateTime }, 
            (error, results, fields)=>{
                if(error) {
                    reject(false);
                    throw error;
                }
                resolve(true);
                console.log("insert result: ", results);
            }
        );
    });
}

/**
 * 查询短域名
 * @param originUrl 原始域名
 * @returns 
 */
function queryShortUrl(originUrl: string): Promise<string>{
    const sql = 'SELECT short_url from short_url.short_url where origin_url = ?';
    return new Promise((resolve, reject)=>{
        db.pool.query(
            sql, 
            [originUrl], 
            (error, results, fields)=>{
                if(error) {
                    reject("queryShortUrl failed");
                    throw error;
                }
                resolve(results.length > 0 ? results[0].short_url : null);
                console.log("queryShortUrl result: ", results);
            });
    });
    
}

/**
 * 查询原始域名
 * @param shortUrl 短域名
 * @returns 
 */
function queryOriginUrl(shortUrl: string): Promise<string>{
    const sql = 'SELECT origin_url from short_url.short_url where short_url = ?';
    return new Promise((resolve, reject)=>{
        db.pool.query(
            sql, 
            [shortUrl], 
            (error, results, fields)=>{
                if(error) {
                    reject("queryOriginUrl failed");
                    throw error;
                }
                resolve(results.length > 0 ? results[0].origin_url : null);
                console.log("queryOriginUrl result: ", results);
            });
    });
    
}

export default {
    saveShortUrl,
    queryShortUrl,
    queryOriginUrl
}