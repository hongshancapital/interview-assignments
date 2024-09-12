class TimedTask {
    public constructor({
                           callback,
                           interval,
                           startAt,
                           count,
                       }: {
        callback: Function;
        interval: number;
        startAt?: Date;
        count?: number;
    }) {
        if (count !== undefined && (count <= 0 || (count | 0) !== count)) {
            throw new Error(`illegal count:${count}`);
        }

        this.callback = callback;
        this.interval = interval;
        if (count !== undefined) {
            this.count = count;
        }

        const now = new Date();
        if (startAt && startAt < now) {
            throw new Error(`illegal startAt:${startAt}`);
        }

        if (startAt) {
            this.startAt = startAt;
            this.lastExecuteTime = startAt.getTime() - interval;
            this.handle = this.setNextExecute({ time: startAt.getTime() - now.getTime() });
        } else {
            callback();
            this.times++;
            this.startAt = now;
            this.lastExecuteTime = now.getTime() - interval;
            if (!this.isFinish()) {
                this.handle = this.setNextExecute({ time: interval });
            }
        }
    }

    protected setNextExecute({ time }: { time: number }) {
        return setTimeout(() => {
            this.callback();
            this.times++;
            this.lastExecuteTime += this.interval;
            if (!this.isFinish()) {
                const delay = Math.max(0, this.lastExecuteTime + this.interval - new Date().getTime());
                this.handle = this.setNextExecute({ time: delay });
            } else {
                this.handle = null;
            }
        }, time);
    }

    public cancel() {
        if (this.handle) {
            clearTimeout(this.handle);
            this.handle = null;
        }
    }

    isFinish() {
        return this.count !== undefined && this.times >= this.count;
    }

    protected startAt: Date;

    protected count?: number;

    protected times: number = 0;

    protected interval: number;

    protected callback: Function;

    public handle: NodeJS.Timer | null;

    protected lastExecuteTime: number;
}

export default TimedTask;
