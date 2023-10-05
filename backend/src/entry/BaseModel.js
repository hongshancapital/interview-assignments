"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const objection_1 = require("objection");
class BaseModel extends objection_1.Model {
    $beforeInsert() {
        this.createdAt = this.datetimeString();
    }
    // mysql format 0000-00-00 00:00:00
    datetimeString() {
        return new Date()
            .toISOString()
            .slice(0, 19)
            .replace('T', ' ');
    }
}
exports.default = BaseModel;
