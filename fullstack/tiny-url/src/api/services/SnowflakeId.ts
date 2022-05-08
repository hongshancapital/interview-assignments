/**
 * 48-bit  segment algorithm distributed unique ID generator
 *
 *
 * The 48 bits  structure is as follows:
 * |---|--- segmentID ---|--workerid--|--- salt ---|
 * |-1-|----- 36 bit ----|-- 3 bit  --|--- 8 bit --|
 * |   |                 |            |            |
 *   0   0000000000000000       000       00000000
 *
 * 1, the high-order 1bit is fixed to 0 to indicate a positive number
 * 2, 36bit segementId
 * 3, 3bit workerId
 * 4, 8bit salt
 * The minimum length for generating base62 (snowflakeid) can be controlled by changing the number of workerid+salt bits,18=>4,24=>5,30=>6
 * The base62 value of length 8 corresponds to a binary length of 48, and the corresponding maximum decimal value is 68719476735,
 * so the base62 value will exceed the 8-bit length when it exceeds 68719476735
 *
 *
 */
import Singleton from '../utils/Singleton';
import {getIPAddress, toCodePoints, mathSum, randomRange} from '../utils/utils';
import config from 'config';
import base62 from 'base62';

import chalk from 'chalk';

export class SnowflakeId extends Singleton {
    private salt!: number;
    private salltBits!: number;
    private maxSalt!: number;
    private workerId!: number;
    private workerIdBits!: number;
    private maxWorkerId!: number;
    private workerIdLeftShift!: number;
    private segmentIdLeftShift!: number;
    private MaxSegmentIdBits!: number;
    private MaxSegmentId: number;

    constructor() {
        super();
        this.salltBits = config.get('snowflakeid.salltBits');
        this.workerIdBits = config.get('snowflakeid.workerIdBits');
        this.maxWorkerId = Math.pow(2, this.workerIdBits) - 1; //-1 ^ (-1 << this.workerIdBits);
        this.maxSalt = Math.pow(2, this.salltBits) - 1; //-1 ^ (-1 << this.salltBits);
        this.workerId = this.getWorkId();
        this.workerIdLeftShift = this.salltBits;
        this.segmentIdLeftShift = this.workerIdBits + this.salltBits;
        this.MaxSegmentIdBits =
            (config.get('snowflakeid.snowflakeIdBits') as number) - this.workerIdBits - this.salltBits - 1;
        this.MaxSegmentId = Math.pow(2, this.MaxSegmentIdBits) - 1;
    }

    //get workid by ip || a random number in a certain range
    getWorkId(): number {
        try {
            const ipAddress = getIPAddress();
            if (!ipAddress) {
                throw new Error('Host IP address is error.');
            }
            const sums = mathSum(toCodePoints(ipAddress));
            return sums % this.maxWorkerId;
        } catch (err) {
            //console.log(chalk.red(err));
            return randomRange(0, this.maxWorkerId);
        }
    }
    //get salt by a random number in a certain range
    getSalt(): number {
        return randomRange(0, this.maxSalt);
    }

    //get a 48-bit binary number and convert it to 62-bit through a similar snowflake algorithm
    getId(segmentId: number, debug: boolean = false): string {
        if (segmentId > this.MaxSegmentId) {
            //throw new Error('The length of shortid exceed 8 bits');
            console.log(chalk.red('The length of shortid exceed 8 bits'));
            return '-1';
        }
        this.salt = this.getSalt();
        const sid =
            segmentId * Math.pow(2, this.segmentIdLeftShift) +
            this.workerId * Math.pow(2, this.workerIdLeftShift) +
            this.salt;

        if (debug) {
            console.log(chalk.green('|--------------------------------|'));
            console.log('   salt: ' + this.salt);
            console.log('   salltBits: ' + this.salltBits);
            console.log('   maxSalt: ' + this.maxSalt);
            console.log('   workerId: ' + this.workerId);
            console.log('   workerIdBits: ' + this.workerIdBits);
            console.log('   maxWorkerId: ' + this.maxWorkerId);
            console.log('   workerIdLeftShift: ' + this.workerIdLeftShift);
            console.log('   segmentIdLeftShift: ' + this.segmentIdLeftShift);
            console.log('   MaxSegmentIdBits: ' + this.MaxSegmentIdBits);
            console.log('   MaxSegmentId: ' + this.MaxSegmentId);
            console.log('   segmentId: ' + chalk.blue(segmentId));
            console.log('   sid: ' + chalk.magenta(sid));
            console.log('   base62: ' + chalk.cyan(base62.encode(sid)));
            console.log(chalk.green('|-------------------------------|'));
        }

        return base62.encode(sid);
    }
}

const SnowflakeIdinstance = SnowflakeId.getInstance();
export default SnowflakeIdinstance;
