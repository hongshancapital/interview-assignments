export enum ContentType {
    ApplicationJson = 'application/json'
}

export enum StatusCode {
    Success = 200,
    ResourceNotFound = 404,
    UnprocessableEntity = 422,
    ServerFailed = 500,
    Redirect = 302,
}

export class ResponseData<T> {
    message: string;
    payload: T | null;
    constructor(m: string = 'success', p: T | undefined = undefined) {
        this.message = m
        this.payload = null;
        if (p !== undefined) {
            this.payload = p
        }
    }
}
