import NeDB from 'nedb';

// 采用NeDB非关系型数据库
const db = new NeDB({
    autoload: true,
    filename: './db/data/url.db'
})

/**
 * 往数据库里插入对象
 * @param key 8位hash值
 * @param sourceUrl 传入的长链接
 * @returns 插入该对象，例如{"key":"6232ac1e","sourceUrl":"https://www.mt.com/aaa/bbb/ccc/index.html?x=1%23a/?foo=2","_id":"tSAddLgOLDgQ8K63"}
 */
export const insertShortDomain = (key: string, sourceUrl: string): Promise<boolean> => {
    return new Promise((resolve, reject) => {
        db.insert({
            key,
            sourceUrl
        }, (err, newDocs) => {
            if (err) {
                reject({ msg: JSON.stringify(err) });
            }
            // console.log('newDocs', newDocs);
            resolve(true)
        })
    })
}

/**
 * 根据hash值查询长链接地址
 * @param key 8位hash值
 * @returns 长链接地址
 */
export const getLongDomain = (key: string): Promise<string> => {
    // console.log('get key', key)
    return new Promise((resolve, reject) => {
        db.findOne({ key }, (err, docs) => {
            // console.log('get data', err, docs)
            if (err) {
                reject({ msg: JSON.stringify(err) });
            }
            if (!docs || docs.length === 0) {
                resolve('')
            }
            resolve(docs?.sourceUrl || '');
        });
    })
}
