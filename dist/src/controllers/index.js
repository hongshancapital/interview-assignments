"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const fs_1 = __importDefault(require("fs"));
const files = fs_1.default.readdirSync(__dirname).filter((file) => file.indexOf("index") < 0);
const controllers = {};
for (const file of files) {
    if (file.toLowerCase().endsWith("js")) {
        const controller = require(`./${file}`);
        controllers[`${file.replace(/\.js/, "")}`] = controller;
    }
}
exports.default = controllers;
//# sourceMappingURL=index.js.map