"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.HttpErrorCode = exports.errorMessages = exports.HttpErrorStatusCodeEnum = void 0;
var HttpErrorStatusCodeEnum;
(function (HttpErrorStatusCodeEnum) {
    HttpErrorStatusCodeEnum[HttpErrorStatusCodeEnum["BadData"] = 400] = "BadData";
    HttpErrorStatusCodeEnum[HttpErrorStatusCodeEnum["NotAuthorized"] = 401] = "NotAuthorized";
    HttpErrorStatusCodeEnum[HttpErrorStatusCodeEnum["Forbidden"] = 403] = "Forbidden";
    HttpErrorStatusCodeEnum[HttpErrorStatusCodeEnum["NotFound"] = 404] = "NotFound";
    HttpErrorStatusCodeEnum[HttpErrorStatusCodeEnum["Conflict"] = 409] = "Conflict";
    HttpErrorStatusCodeEnum[HttpErrorStatusCodeEnum["InternalServerError"] = 500] = "InternalServerError";
    HttpErrorStatusCodeEnum[HttpErrorStatusCodeEnum["ServiceUnavailable"] = 503] = "ServiceUnavailable";
})(HttpErrorStatusCodeEnum = exports.HttpErrorStatusCodeEnum || (exports.HttpErrorStatusCodeEnum = {}));
exports.errorMessages = {
    400: 'Bad Data',
    401: 'Not Authorized',
    403: 'Forbidden',
    404: 'Not Found',
    409: 'Conflict',
    500: 'Internal Server Error',
    503: 'Service Unavailable',
};
var HttpErrorCode;
(function (HttpErrorCode) {
    HttpErrorCode["systemError"] = "SV00001";
    HttpErrorCode["typedParamInvalid"] = "SV00002";
    HttpErrorCode["bodyTypeInvalid"] = "SV00003";
    HttpErrorCode["queryTypeInvalid"] = "SV00004";
    HttpErrorCode["outputTypeInvalid"] = "SV00005";
    HttpErrorCode["genShortDNSFailed"] = "SV01001";
    HttpErrorCode["getLongDNSFailed"] = "SV01002";
})(HttpErrorCode = exports.HttpErrorCode || (exports.HttpErrorCode = {}));
