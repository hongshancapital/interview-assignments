"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const mongoose_1 = require("mongoose");
const itSchema = new mongoose_1.Schema({
    shorturl: {
        type: String,
        unique: true,
        index: true,
    },
    url: {
        type: String,
        unique: true,
        index: true,
    },
    _createTime: { type: Date, default: Date.now }
}, {
    versionKey: false
});
itSchema.virtual('createTime')
    .set(function (value) { this._createTime = value; })
    .get(function () { return this._createTime.toLocaleString(); });
exports.default = mongoose_1.model('Url', itSchema);
//# sourceMappingURL=url.js.map