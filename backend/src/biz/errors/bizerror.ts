// 错误码规范，code + message

// "0" 为成功
export const ErrorCodeOk = "0";

export class BizError extends Error {
    code: string;

    constructor(code: string, message: string) {
        super(message);
        this.name = 'BizError';
        this.code = code;
        // Capture the current stack trace
        if (Error.captureStackTrace) {
            Error.captureStackTrace(this, BizError);
        }
    }
}