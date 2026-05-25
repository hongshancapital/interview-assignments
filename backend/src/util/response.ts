export const responseJson = (message: string, bizErrorCode: string, data?: any) => {
    let response = {
        bizErrorCode: bizErrorCode,
        message: message,
        data: data,
    }

    return response;
}