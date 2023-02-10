import ErrCode from '../config/code'

class BaseError extends Error {
    constructor(readonly message: string, readonly code: number) {
        super(message)
    }
}

export class NotFoundError extends BaseError {
    constructor(message: string) {
        super(message, ErrCode.NOT_FOUND)
    }
}

export class InvalidUrlError extends BaseError {
    constructor(message: string) {
        super(message, ErrCode.INVALID_URL)
    }
}

export class InvalidCodeError extends BaseError {
    constructor(message: string) {
        super(message, ErrCode.INVALID_CODE)
    }
}

export class InvalidParamsError extends BaseError {
    constructor(message: string) {
        super(message, ErrCode.INVALID_PARAMS)
    }
}

export class InvalidNumberError extends BaseError {
    constructor(message: string) {
        super(message, ErrCode.INVALID_NUMBER)
    }
}

export class InvalidFlagError extends BaseError {
    constructor(message: string) {
        super(message, ErrCode.INVALID_FLAG)
    }
}