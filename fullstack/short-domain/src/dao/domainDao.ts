import {pool} from '../utils/db';
import {ShortDomainItem} from "../schema/domainSchema";

/**
 * 添加短域名
 * @param domain
 */
export async function addShortDomain(domain: ShortDomainItem): Promise<any> {
    return new Promise((resolve, reject) => {
        const sql = 'insert into t_short_domain (`union_id`, `complete_url`, `create_time`) values(?, ?, ?);';
        pool.query(
            sql,
            [domain.unionId, domain.completeUrl, domain.createTime],
            (err, row) => {
                if (err) {
                    console.log(err);
                    reject(err);
                    return;
                }
                resolve(row);
            }
        );
    })
}

/**
 * 获取短域名
 * @param domain
 */
export async function getShortDomain(domain: string): Promise<any> {
    return new Promise((resolve, reject) => {
        pool.query(
            'SELECT * FROM t_short_domain WHERE union_id = ?',
            [domain],
            (err, [record]: any) => {
                if (err) {
                    reject(err);
                    return;
                }

                resolve(record ? record.complete_url : undefined);
            }
        );
    });

}

