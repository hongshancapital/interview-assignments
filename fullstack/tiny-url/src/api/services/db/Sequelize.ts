import {Dialect, Transaction} from 'sequelize';
import {Sequelize} from 'sequelize-typescript';
import config from 'config';
import {Segments, SegmentsAttributes} from '../../models/SegmentsModel';

import Singleton from '../../utils/Singleton';
import chalk from 'chalk';
//commit it for jest
/**
import consoleStamp from 'console-stamp';
consoleStamp(console, {
    format: ':date(yyyy/mm/dd HH:MM:ss.l) :label',
});
/**/

class Mysql extends Singleton {
    private app_tag: string;
    private storage: BigInt[] = [];
    private _sequelize: Sequelize;

    constructor() {
        super();
        this.app_tag = config.get('mysql.app_tag');
        this._sequelize = new Sequelize(
            config.get('mysql.database'),
            config.get('mysql.username'),
            config.get('mysql.password'),
            {
                //logging: false,
                host: config.get('mysql.host'),
                dialect: config.get('mysql.dirver') as Dialect,
                dialectOptions: {
                    port: config.get('mysql.port'),
                },
            },
        );

        this._sequelize.addModels([Segments]);
        this.connect();
        //uncomment it when want to change segment step(config/MysqlConfig:step)
        //this.updateStep(MysqlConfig.step);
        this.enqueueId();
    }

    async connect() {
        await this._sequelize
            .authenticate()
            .then(() => {
                console.log(chalk.green(`Mysql connection established`));
            })
            .catch(err => {
                console.error(chalk.red('Unable to connect to Mysql:', err));
            });
    }

    //close connection for jest testing
    async close() {
        await this._sequelize.close();
    }
    // update segment step
    async updateStep(Step: bigint) {
        const t = await this._sequelize.transaction({isolationLevel: Transaction.ISOLATION_LEVELS.SERIALIZABLE});
        try {
            const objectToUpdate = {
                step: Step,
            };
            await Segments.update(objectToUpdate, {
                where: {app_tag: this.app_tag},
                transaction: t,
            });
            t.commit();
            console.log(chalk.green(`Segement step has been updated to:${Step} `));
        } catch (err) {
            await t.rollback();
            console.log(chalk.red(err));
        }
    }

    //get segment range through mysql transaction (row lock, transaction_isolation:SERIALIZABLE)
    async getIdRange(): Promise<SegmentsAttributes | undefined> {
        const t = await this._sequelize.transaction({isolationLevel: Transaction.ISOLATION_LEVELS.SERIALIZABLE});
        try {
            const sid = await Segments.findOne({
                raw: true,
                attributes: ['app_tag', 'max_id', 'step', 'update_time'],
                where: {app_tag: this.app_tag},
                transaction: t,
                lock: t.LOCK.UPDATE,
            });
            if (sid !== null) {
                const maxId = BigInt(sid.max_id + sid.step);
                const objectToUpdate = {
                    max_id: maxId,
                };
                await Segments.update(objectToUpdate, {
                    where: {app_tag: this.app_tag},
                    transaction: t,
                });
                t.commit();
                return new Promise((resolve, reject) => {
                    resolve({
                        app_tag: sid.app_tag,
                        max_id: BigInt(sid.max_id),
                        step: BigInt(sid.step),
                        update_time: sid.update_time,
                    });
                });
            }
        } catch (err) {
            await t.rollback();
            console.log(chalk.red(err));
        }
    }

    //put the numbers in the segment range into the queue
    async enqueueId() {
        const idRange = await this.getIdRange();
        if (idRange !== undefined) {
            for (let i = idRange.max_id + 1n; i <= idRange.max_id + idRange.step; i++) {
                this.enqueue(i);
            }
            console.log(
                chalk.green(`====> Enqueue ID from ${idRange.max_id + 1n} to ${idRange.max_id + idRange.step}`),
            );
        }
    }

    //enqueue
    enqueue(item: bigint): void {
        this.storage.push(item);
    }
    //dequeue.when a certain threshold is reached, automatically get data from the database and put it into the queue
    async dequeue(): Promise<BigInt | undefined> {
        //size will be 0 in jest test
        //automatically add data when the queue size is less than the specified threshold
        if (this.size() == 0 || this.size() == Math.floor(Number(config.get('mysql.step')) * 0.1)) {
            await this.enqueueId();
        }
        const segmentId = this.storage.shift();
        return segmentId;
    }
    size(): Number {
        return this.storage.length;
    }
}

const Mysqlinstance = Mysql.getInstance();
export default Mysqlinstance;
