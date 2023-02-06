import { pool } from '../utils/db';
import {ShortDomainItem} from "../schema/domainSchema";
import {RowDataPacket} from "mysql2";

/**
 * 添加短域名
 * @param domain
 */
export async function addShortDomain(domain: ShortDomainItem) {

}

export async function getShortDomain(domain: string): Promise<ShortDomainItem> {
    return new Promise((resolve, reject) => {
        pool.query(
            'SELECT * FROM t_domain WHERE short_domain = $1',
            [domain],
            (err, row: RowDataPacket[]) => {
                if (err) {
                    reject(err);
                    return;
                }

                console.log(row);


                resolve(row);
            }
        );
    });

}

