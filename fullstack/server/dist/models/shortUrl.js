"use strict";
exports.__esModule = true;
var mongoose_1 = require("mongoose");
var shortUrlSchema = new mongoose_1.Schema({
    fullUrl: {
        type: String,
        required: true
    },
    shortUrl: {
        type: String,
        required: true
    },
    createTime: {
        type: Date,
        require: true,
        "default": Date
    }
});
module.exports = (0, mongoose_1.model)('ShortUrl', shortUrlSchema);
