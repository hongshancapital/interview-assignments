"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
const router = express_1.default.Router();
const url_1 = require("../models/url");
router.get('/:code', (req, res, next) => __awaiter(void 0, void 0, void 0, function* () {
    try {
        const urlCode = req.params.code;
        const url = yield url_1.UrlModel.findOne({ urlCode });
        if (url) {
            res.redirect(url.longUrl);
        }
        else {
            res.status(404).json("No url found");
        }
    }
    catch (error) {
        res.status(500).json("Server error");
    }
}));
exports.default = router;
