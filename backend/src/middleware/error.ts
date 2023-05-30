import { ICustomError } from "../types/common";

class CustomError extends Error implements ICustomError {
    public code: string;
    public statusCode: number;
    public errorCode: number;
    public desc: string;
    constructor (message: string) {
        super(message);
        this.name = this.constructor.name;
        this.code = '';
        this.statusCode = 0;
        this.errorCode = 0;
        this.desc = '';
    }
}

export class ParamInvalid extends CustomError {
    constructor (message: string) {
        super(message);
        this.code = 'PARAM_INVALID';
        this.statusCode = 400;
        this.errorCode = 10001;
        this.desc = 'some parameters are invalid.';
    }
}

export class ResourceNotFound extends CustomError {
    constructor (message: string) {
        super(message);
        this.code = 'RESOURCE_NOT_FOUND';
        this.statusCode = 404;
        this.errorCode = 10002;
        this.desc = 'not found';
    }
}