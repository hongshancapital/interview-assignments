"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const dbConect_1 = __importDefault(require("../dbConect"));
class InvalidStrategy extends dbConect_1.default {
    constructor(inputUrl) {
        super();
        this.inputUrl = 'Invalid Url';
    }
    convert(handleUrl) {
        handleUrl(null, this.inputUrl);
    }
    ;
}
exports.default = InvalidStrategy;
