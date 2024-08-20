import { snowflakeOptions } from "./snowflakeOptions"

/**
 * 该算法摘抄于github，此处减少workerId和随机Id的位数为3
 * 去除时间回拨，序列超出限制，雪花漂移算法等操作。
 * 只保留雪花id的基本生成思路
 */
export class Snowflake {

    /**
     * 雪花计算方法，（1-漂移算法|2-传统算法），默认 1
     */
    private Method

    /**
     * 基础时间（ms 单位），不能超过当前系统时间
     */
    private BaseTime

    /**
     * 机器码，必须由外部设定，最大值 2^WorkerIdBitLength-1
     */
    private WorkerId


    /**
     * 机器码位长，默认值 6，取值范围 [1, 15](要求：序列数位长+机器码位长不超过 22)
     */
    private WorkerIdBitLength

    /**
     * 序列数位长，默认值 6，取值范围 [3, 21](要求：序列数位长+机器码位长不超过 22)
     */
    private SeqBitLength

    /**
     * 最大序列数（含），设置范围 [MinSeqNumber, 2^SeqBitLength-1]，默认值 0，表示最大序列数取最大值（2^SeqBitLength-1]）
     */
    private MaxSeqNumber

    /**
     * 最小序列数（含），默认值 5，取值范围 [5, MaxSeqNumber]，每毫秒的前 5 个序列数对应编号 0-4 是保留位，其中 1-4 是时间回拨相应预留位，0 是手工新值预留位
     */
    private MinSeqNumber

    /**
     * 最大漂移次数（含），默认 2000，推荐范围 500-10000（与计算能力有关）
     */
    private TopOverCostCount

    /**
     *
     */
    private _TimestampShift

    /**
     *
     */
    private _CurrentSeqNumber

    /**
     *
     */
    private _LastTimeTick: bigint

    /**
     * 回拨次序, 支持 4 次回拨次序（避免回拨重叠导致 ID 重复）
     */
    private _TurnBackTimeTick: bigint

    /**
     *
     */
    private _TurnBackIndex

    /**
     *
     */
    private _IsOverCost

    /**
     *
     */
    private _OverCostCountInOneTerm

    /**
     *Creates an instance of Genid.
     * @author zhupengfeivip
     *     BaseTime: 1577836800000,  // 基础时间（ms 单位），默认2020年1月1日，不能超过当前系统时间，一旦投入使用就不能再更改，更改后产生的ID可能会和以前的重复
     *     WorkerId: Number, // 机器码，必须由外部设定，最大值 2^WorkerIdBitLength-1
     *     WorkerIdBitLength: 6,   // 机器码位长，默认值 6，取值范围 [1, 15](要求：序列数位长+机器码位长不超过 22)
     *     SeqBitLength: 6,   // 序列数位长，默认值 6，取值范围 [3, 21](要求：序列数位长+机器码位长不超过 22)
     *     MaxSeqNumber: 5, // 最大序列数（含），设置范围 [MinSeqNumber, 2^SeqBitLength-1]，默认值 0，表示最大序列数取最大值（2^SeqBitLength-1]）
     *     MinSeqNumber: 5, // 最小序列数（含），默认值 5，取值范围 [5, MaxSeqNumber]，每毫秒的前 5 个序列数对应编号 0-4 是保留位，其中 1-4 是时间回拨相应预留位，0 是手工新值预留位
     *     TopOverCostCount: 2000// 最大漂移次数（含），默认 2000，推荐范围 500-10000（与计算能力有关）
     * }} options
     * @memberof Genid
     * @param options
     */
    constructor(options: snowflakeOptions) {
        if (options.workerId === undefined)
            throw new Error("lost WorkerId")

        // 1.BaseTime 2020年1月1日 Wed, 01 Jan 2020 00:00:00 GMT 0时区的2020年1月1日
        const BaseTime = 1577836800000
        if (!options.baseTime || options.baseTime < 0)
            options.baseTime = BaseTime

        // 2.WorkerIdBitLength
        const WorkerIdBitLength = 6
        if (!options.workerIdBitLength || options.workerIdBitLength < 0)
            options.workerIdBitLength = WorkerIdBitLength

        // 4.SeqBitLength
        const SeqBitLength = 6
        if (!options.seqBitLength || options.seqBitLength < 0)
            options.seqBitLength = SeqBitLength

        // 5.MaxSeqNumber
        if (options.maxSeqNumber == undefined || options.maxSeqNumber <= 0)
            options.maxSeqNumber = (1 << options.seqBitLength) - 1

        // 6.MinSeqNumber
        const MinSeqNumber = 5
        if (options.minSeqNumber == undefined || options.minSeqNumber < 0)
            options.minSeqNumber = MinSeqNumber

        // 7.Others
        const topOverCostCount = 2000
        if (options.topOverCostCount == undefined || options.topOverCostCount < 0)
            options.topOverCostCount = topOverCostCount


        if (options.method !== 2)
            options.method = 1
        else
            options.method = 2

        this.Method = BigInt(options.method)
        this.BaseTime = BigInt(options.baseTime)
        this.WorkerId = BigInt(options.workerId)
        this.WorkerIdBitLength = BigInt(options.workerIdBitLength)
        this.SeqBitLength = BigInt(options.seqBitLength)
        this.MaxSeqNumber = BigInt(options.maxSeqNumber)
        this.MinSeqNumber = BigInt(options.minSeqNumber)
        this.TopOverCostCount = BigInt(options.topOverCostCount)

        const timestampShift = this.WorkerIdBitLength + this.SeqBitLength
        const currentSeqNumber = this.MinSeqNumber

        this._TimestampShift = timestampShift
        this._CurrentSeqNumber = currentSeqNumber

        this._LastTimeTick = BigInt(0)
        this._TurnBackTimeTick = BigInt(0)
        this._TurnBackIndex = 0
        this._IsOverCost = false
        this._OverCostCountInOneTerm = 0
    }

    /**
     * 常规雪花算法
     * @returns
     */
    private NextNormalId() {
        const currentTimeTick = this.GetCurrentTimeTick()
        // 时间追平时，_TurnBackTimeTick 清零
        if (this._TurnBackTimeTick > 0) {
            this._TurnBackTimeTick = BigInt(0)
        }
        if (currentTimeTick > this._LastTimeTick) {
            this._LastTimeTick = currentTimeTick
            this._CurrentSeqNumber = this.MinSeqNumber
            return this.CalcId(this._LastTimeTick)
        }

        return this.CalcId(this._LastTimeTick)
    }

    /**
     * 生成ID
     * @param useTimeTick 时间戳
     * @returns
     */
    private CalcId(useTimeTick: bigint) {
        //ID组成 1.相对基础时间的时间差 | 2.WorkerId | 3.序列数
        //时间差，是生成ID时的系统时间减去 BaseTime 的总时间差（毫秒单位）
        const result = BigInt(useTimeTick << this._TimestampShift) + BigInt(this.WorkerId << this.SeqBitLength) + BigInt(this._CurrentSeqNumber)
        this._CurrentSeqNumber++
        return result
    }

    /**
     *
     * @returns
     */
    private GetCurrentTimeTick() {
        const millis = BigInt((new Date()).valueOf())
        return millis - this.BaseTime
    }


    /**
     * 生成ID
     * @returns 始终输出number类型，超过时throw error
     */
    public nextNumber(): number {
        let id = this.NextNormalId()
        if (id >= 9007199254740992n)
            throw Error(`${id.toString()} over max of Number 9007199254740992`)

        return parseInt(id.toString())
    }

    /**
     * 生成ID
     * @returns 根据输出数值判断，小于number最大值时输出number类型，大于时输出bigint
     */
    public nextId(): number | bigint {
        let id = this.NextNormalId()
        if (id >= 9007199254740992n)
            return id
        else
            return parseInt(id.toString())
    }

    /**
     * 生成ID
     * @returns 始终输出bigint类型
     */
    public nextBigId(): bigint {
        return this.NextNormalId()
    }

    /**
     * 10 进制 数据转62 进制数据
     */
    public next62Id(): string {
        let chars='0123456789abcdefghigklmnopqrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ'.split('');
        let radix=chars.length;
        let qutient= this.nextNumber();
        let arr= [];
        while (qutient) {
            let mod = qutient % radix;
            qutient=(qutient - mod) / radix;
            arr.unshift(chars[mod]);
        }
        return arr.join('');
    }
}
