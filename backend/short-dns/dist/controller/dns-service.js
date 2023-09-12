"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.prototype.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (g && (g = 0, op[0] && (_ = 0)), _) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.getLongUrl = exports.genShortDns = void 0;
var env_1 = require("../config/env");
var http_error_1 = require("../middleware/http-error");
var redis_1 = require("../middleware/redis");
var errors_1 = require("../types/errors");
var MD5 = __importStar(require("ts-md5"));
var chars = [
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
    'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
    'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
    'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
    'Y', 'Z', '-', '_',
];
var r = new redis_1.RedisUtils();
var genShortDns = function (data, baseUrl) { return __awaiter(void 0, void 0, void 0, function () {
    var hashStr, charsLength, resultChars, i, shortStr, retryCount, isSuccess;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                hashStr = MD5.Md5.hashStr("".concat(data.url, "-").concat(data.exp, "-").concat(new Date().getTime()));
                charsLength = chars.length;
                resultChars = [];
                for (i = 0; i < hashStr.length; i += 2) {
                    resultChars.push(chars[parseInt("0x".concat(hashStr[i]).concat(hashStr[i + 1]), 16) % charsLength]);
                }
                shortStr = resultChars.slice(0, env_1.SHORT_DNS_MAX_LENGTH).join('');
                retryCount = 0;
                isSuccess = false;
                _a.label = 1;
            case 1:
                if (!(retryCount < env_1.SHORT_DNS_MAX_RETRY)) return [3 /*break*/, 3];
                return [4 /*yield*/, r.set(shortStr, data.url, data.exp)];
            case 2:
                if (_a.sent()) {
                    isSuccess = true;
                    return [3 /*break*/, 3];
                }
                retryCount++;
                shortStr = resultChars.slice(retryCount, env_1.SHORT_DNS_MAX_LENGTH + retryCount).join('');
                return [3 /*break*/, 1];
            case 3:
                if (!isSuccess) {
                    throw new http_error_1.HttpError(403, errors_1.HttpErrorCode.genShortDNSFailed);
                }
                return [2 /*return*/, { url: "".concat(baseUrl).concat(shortStr) }];
        }
    });
}); };
exports.genShortDns = genShortDns;
var getLongUrl = function (shortStr) { return __awaiter(void 0, void 0, void 0, function () {
    var url;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0: return [4 /*yield*/, r.get(shortStr)];
            case 1:
                url = _a.sent();
                if (url === '') {
                    throw new http_error_1.HttpError(403, errors_1.HttpErrorCode.getLongDNSFailed);
                }
                return [2 /*return*/, { url: url }];
        }
    });
}); };
exports.getLongUrl = getLongUrl;
