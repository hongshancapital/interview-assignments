declare interface HttpArgument {
    params?: Record<string, any>;// eslint-disable-line @typescript-eslint/no-explicit-any
    data?: Record<string, unknown>;
    headers?: Record<string, string | string[] | undefined>;
}

declare interface ExceptionError {
    info: string;
    [key: string]: unknown;
}

declare interface HttpException {
    httpInfo: string;
    status: number;
    type?: string;
    error: ExceptionError;
}

declare interface ApiResult {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    data?: any;
    error?: HttpException;
}

type SupportLanguageType = 'zh-cn' | 'zh-tw' | 'en';
