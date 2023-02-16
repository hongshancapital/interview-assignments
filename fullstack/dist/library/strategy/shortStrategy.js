"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const dbConect_1 = __importDefault(require("../dbConect"));
class ShortStrategy extends dbConect_1.default {
    constructor(inputUrl) {
        super();
        this.shortUrl = inputUrl;
    }
    convert(handleUrl) {
        this.findLongDomain(this.shortUrl, (err, longUrl) => {
            if (longUrl) {
                handleUrl(err, longUrl);
            }
            else {
                handleUrl(null, this.shortUrl); // Directly handle the short URL while there is no long domain URL in database;
            }
        });
    }
    ;
}
exports.default = ShortStrategy;
