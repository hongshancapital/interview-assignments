"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const util_1 = require("../utilites/util");
const invalidStrategy_1 = __importDefault(require("./strategy/invalidStrategy"));
const longStrategy_1 = __importDefault(require("./strategy/longStrategy"));
const shortStrategy_1 = __importDefault(require("./strategy/shortStrategy"));
function default_1(inputUrl) {
    let convertDomainHandler;
    if (!(0, util_1.validateUrl)(inputUrl)) {
        convertDomainHandler = new invalidStrategy_1.default(inputUrl);
    }
    else if ((0, util_1.isShortDomain)(inputUrl)) {
        convertDomainHandler = new shortStrategy_1.default(inputUrl);
    }
    else {
        convertDomainHandler = new longStrategy_1.default(inputUrl);
    }
    return convertDomainHandler;
}
exports.default = default_1;
