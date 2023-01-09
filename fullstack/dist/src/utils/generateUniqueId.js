"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const nanoid_1 = require("nanoid");
/**
 * TODO
 * 生成唯一码，由于8位限制，可在新码生成前在库中校验。 内部使用的话，生成短链的高并发概率较低，性能可不必过于严格。在查询阶段考虑性能
 */
exports.default = () => (0, nanoid_1.nanoid)(8);
