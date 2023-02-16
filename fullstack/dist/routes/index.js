"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const db_1 = __importDefault(require("../config/db"));
const factory_1 = __importDefault(require("../library/factory"));
db_1.default.connect((err) => console.dir(err));
var router = express_1.default.Router();
router.post('/', function (req, res) {
    const { origUrl } = req.body;
    (0, factory_1.default)(origUrl).convert(function (err, convertedUrl) {
        if (err) {
            console.log('Operated Database Error:');
            console.error(err);
            return;
        }
        res.send(convertedUrl);
    });
});
exports.default = router;
