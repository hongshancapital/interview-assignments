import { ErrorCodes, SuccessCodes } from "./codes";

class ResponseModel {
    readonly code: number;
    readonly message: string;
    readonly data: any;

    constructor(code: number, message: string = '', data: any = null) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

class ServerResponse {
    // --- GENERAL
    static Success(data: any = null): ResponseModel {
        return new ResponseModel(
            SuccessCodes.OK,
            'OK',
            data
        )
    }

    static Error(errorCode: number, message: string, data: any = null): ResponseModel {
        if (message == '') {
            message = 'Undefined error code...'
        }

        return new ResponseModel(
            errorCode,
            message,
            data,
        )
    }

    // --- SPECIFIC
    static UnimplementedMethod(): ResponseModel {
        return new ResponseModel(
            ErrorCodes.UNIMPLEMENTED_METHODS,
            'Non-existent endpoint',
            null,
        )
    }

    static ValidationError(message: string = 'Please check your body payload again'): ResponseModel {
        return new ResponseModel(
            ErrorCodes.INVALID_INPUT,
            message,
            null,
        )
    }

    static GeneralError(data: any = null): ResponseModel {
        if (data instanceof Error) {
            data = {
                error: {
                    name: data.name,
                    message: data.message,
                    stack: data.stack,
                }
            };
        }

        return new ResponseModel(
            ErrorCodes.GENERAL_ERR,
            'An error occurred',
            data,
        )
    }
}

export default ServerResponse;
