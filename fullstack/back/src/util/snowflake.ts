/**
 * SnowFlake的结构如下(共64bits，每部分用-分开):
 *   0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000
 *   |   ----------------------|----------------------   --|--   --|--   -----|------
 * 1bit标识                41bit 时间戳                  数据标识id 机器id     序列号id
*/

// 雪花算法
export default class SnowFlake {
    // private readonly twepoch = 1548988646430n
    private readonly twepoch = 1661397758514n

    private readonly workerIdBits: bigint = 5n // 标识ID
    private readonly dataCenterIdBits: bigint = 5n // 机器ID
    private readonly sequenceBits: bigint = 12n // 序列ID

    private readonly maxWorkerId: bigint = -1n ^ (-1n << this.workerIdBits)
    private readonly maxDataCenterId: bigint =
        -1n ^ (-1n << this.dataCenterIdBits)
    private readonly sequenceMask: bigint = -1n ^ (-1n << this.sequenceBits)

    private readonly workerIdShift: bigint = this.sequenceBits
    private readonly dataCenterIdShift: bigint =
        this.sequenceBits + this.workerIdBits
    private readonly timestampLeftShift: bigint =
        this.dataCenterIdShift + this.dataCenterIdBits

    private sequence: bigint = 0n
    private lastTimestamp: bigint = -1n

    private readonly workerId: bigint
    private readonly dataCenterId: bigint
    constructor(workerId: bigint, dataCenterId: bigint) {
        if (workerId > this.maxWorkerId || workerId < 0n) {
            throw new Error(
                `workerId can't be greater than ${this.maxWorkerId} or less than 0`,
            )
        }
        if (dataCenterId > this.maxDataCenterId || dataCenterId < 0n) {
            throw new Error(
                `dataCenterId can't be greater than ${this.maxDataCenterId} or less than 0`,
            )
        }
        this.workerId = workerId
        this.dataCenterId = dataCenterId
        return this
    }

    public nextId(): bigint {
        let timestamp = this.currentLinuxTime()
        const diff = timestamp - this.lastTimestamp

        if (diff < 0n) {
            throw new Error(
                `Clock moved backwards. Refusing to generate id for ${-diff} milliseconds`,
            )
        }

        if (diff === 0n) {
            this.sequence = (this.sequence + 1n) & this.sequenceMask
            if (this.sequence == 0n) {
                timestamp = this.tilNextMillis(this.lastTimestamp)
            }
        } else {
            this.sequence = 0n
        }

        this.lastTimestamp = timestamp

        return (
            ((timestamp - this.twepoch) << this.timestampLeftShift) |
            (this.dataCenterId << this.dataCenterIdShift) |
            (this.workerId << this.workerIdShift) |
            this.sequence
        )
    }

    private tilNextMillis(lastTimeStamp: bigint) {
        let timestamp: bigint = this.currentLinuxTime()
        while (timestamp <= lastTimeStamp) {
            timestamp = this.currentLinuxTime()
        }
        return timestamp
    }

    private currentLinuxTime(): bigint {
        return BigInt(new Date().valueOf())
    }
}