import { errorCodeMap, getENV } from '@/configs';

// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
global.Exception = class Exception extends Error {
    public message: string;
    public source: Array<string> = [];
    public code!: string;
    public status!: number;
    public reason?: Array<string>;

    constructor(error?: string | InstanceException | Error, code?: string, reason?: Array<string>) {
        super();

        // message
        if (typeof error === 'string') {
            this.message = error;
        } else {
            this.message = error?.message || 'inner server error!';
            // eslint-disable-next-line @typescript-eslint/ban-ts-comment
            // @ts-ignore
            this.source = Array.from(error.source || '');
            // eslint-disable-next-line @typescript-eslint/ban-ts-comment
            // @ts-ignore
            this.code = error.code;
            // eslint-disable-next-line @typescript-eslint/ban-ts-comment
            // @ts-ignore
            this.status = error.status;
            // eslint-disable-next-line @typescript-eslint/ban-ts-comment
            // @ts-ignore
            this.reason = error.reason;
        }

        // source
        const serverName = getENV('SERVER_NAME');

        if (serverName && !this.source.includes(serverName)) {
            this.source.push(serverName);
        }

        // code
        if (code && !this.code) {
            this.code = code;
        }

        if (!this.code) {
            this.code = 'INTERNAL_SERVER_ERROR';
        }

        // status
        if (!this.status) {
            for (const code in errorCodeMap) {
                if (errorCodeMap[code].includes(this.code)) {
                    this.status = parseInt(code);
                    break;
                }
            }

            if (!this.status) {
                this.status = 500;
            }
        }

        // reason
        if (!this.reason) {
            this.reason = [];
        }
        if (reason && reason.length > 0) {
            this.reason.push(...reason);
        }
    }
};
