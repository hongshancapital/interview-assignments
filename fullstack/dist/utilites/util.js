"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.isShortDomain = exports.isLongDomain = exports.validateUrl = void 0;
const url_1 = __importDefault(require("url"));
const constant_1 = require("./constant");
function validateUrl(value) {
    return /[(http(s)?):\/\/(www\.)?a-zA-Z0-9@:%._\+~#=]{2,256}\.[a-z]{2,6}\b([-a-zA-Z0-9@:%_\+.~#?&//=]*)/i.test(value);
}
exports.validateUrl = validateUrl;
function isLongDomain(value) {
    const { path, hash } = url_1.default.parse(value);
    return (path ? path.substring(1) : '').length + (hash ? hash : '').length > constant_1.SHORT_LENGTH;
}
exports.isLongDomain = isLongDomain;
function isShortDomain(value) {
    const { path, hash } = url_1.default.parse(value);
    return (path ? path.substring(1) : '').length + (hash ? hash : '').length <= constant_1.SHORT_LENGTH;
}
exports.isShortDomain = isShortDomain;
