"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const assert_1 = require("assert");
const number62_1 = __importDefault(require("../../utils/number62"));
describe('number62', function () {
    describe('#encode()', function () {
        it('number62.encode(36) should equal "a"', function () {
            (0, assert_1.equal)(number62_1.default.encode(36), 'a');
        });
        it('number62.encode(62) should equal "10"', function () {
            (0, assert_1.equal)(number62_1.default.encode(62), '10');
        });
        it('number62.encode(Math.pow(62,2)) should equal "100"', function () {
            (0, assert_1.equal)(number62_1.default.encode(Math.pow(62, 2)), '100');
        });
        it('number62.encode(Math.pow(62,2),7) should equal "0000100"', function () {
            (0, assert_1.equal)(number62_1.default.encode(Math.pow(62, 2), 7), '0000100');
        });
    });
    describe('#decode()', function () {
        it('number62.decode(a) should equal 36', function () {
            (0, assert_1.equal)(number62_1.default.decode('a'), 36);
        });
        it('number62.decode(10) should equal 62', function () {
            (0, assert_1.equal)(number62_1.default.decode('10'), 62);
        });
        it('number62.decode(100) should equal Math.pow(62,2)', function () {
            (0, assert_1.equal)(number62_1.default.decode('100'), Math.pow(62, 2));
        });
        it('number62.decode(0000100) should equal Math.pow(62,2)', function () {
            (0, assert_1.equal)(number62_1.default.decode('0000100'), Math.pow(62, 2));
        });
    });
});
//# sourceMappingURL=number62.js.map