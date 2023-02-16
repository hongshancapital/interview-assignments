"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const dbConect_1 = __importDefault(require("../dbConect"));
const constant_1 = require("../../utilites/constant");
class LongStrategy extends dbConect_1.default {
    constructor(inputUrl) {
        super();
        this.longUrl = inputUrl;
    }
    convert(handleUrl) {
        this.findShortDomain(this.longUrl, (err, shortUrl) => {
            if (shortUrl) {
                handleUrl(err, shortUrl);
            }
            else {
                console.log('---2---');
                const urlId = require('nanoid')(constant_1.SHORT_LENGTH);
                const shortDomainUrl = `${constant_1.BASE_URL}/${urlId}`;
                this.insertDatabase(this.longUrl, shortDomainUrl, function (err, data) {
                    handleUrl(err, data);
                });
            }
        });
    }
    ;
}
exports.default = LongStrategy;
