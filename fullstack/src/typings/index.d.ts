export interface BizResponse {
    code: number,
    message: string,
    data?: {
        [index: string]: any
    }
}
