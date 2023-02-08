declare interface httpArgument {
    params?: Record<string, any>;
    data?: Record<string, unknown>;
    headers?: Record<string, string | string[] | undefined>
}

declare interface InstanceException {
    message: string;
    source: Array<string>;
    code: string;
    status: number;
    reason?: Array<string>;
}

declare interface ExceptionConstructor {
    new(messageOrErrorOrException: string | InstanceException | Error, code?: string, reason?: Array<string>): InstanceException;
    readonly prototype: InstanceException;
}

declare const Exception: ExceptionConstructor;

declare namespace Express {
    interface Response {
        success: (result?: unknown) => void
    }

    interface Request {

    }
}

type KeysOf<T> = { [P in keyof T]?: T[P] | any };
