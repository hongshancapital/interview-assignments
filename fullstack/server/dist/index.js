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
var __generator = (this && this.__generator) || function (thisArg, body) {
    var _ = { label: 0, sent: function() { if (t[0] & 1) throw t[1]; return t[1]; }, trys: [], ops: [] }, f, y, t, g;
    return g = { next: verb(0), "throw": verb(1), "return": verb(2) }, typeof Symbol === "function" && (g[Symbol.iterator] = function() { return this; }), g;
    function verb(n) { return function (v) { return step([n, v]); }; }
    function step(op) {
        if (f) throw new TypeError("Generator is already executing.");
        while (g && (g = 0, op[0] && (_ = 0)), _) try {
            if (f = 1, y && (t = op[0] & 2 ? y["return"] : op[0] ? y["throw"] || ((t = y["return"]) && t.call(y), 0) : y.next) && !(t = t.call(y, op[1])).done) return t;
            if (y = 0, t) op = [op[0] & 2, t.value];
            switch (op[0]) {
                case 0: case 1: t = op; break;
                case 4: _.label++; return { value: op[1], done: false };
                case 5: _.label++; y = op[1]; op = [0]; continue;
                case 7: op = _.ops.pop(); _.trys.pop(); continue;
                default:
                    if (!(t = _.trys, t = t.length > 0 && t[t.length - 1]) && (op[0] === 6 || op[0] === 2)) { _ = 0; continue; }
                    if (op[0] === 3 && (!t || (op[1] > t[0] && op[1] < t[3]))) { _.label = op[1]; break; }
                    if (op[0] === 6 && _.label < t[1]) { _.label = t[1]; t = op; break; }
                    if (t && _.label < t[2]) { _.label = t[2]; _.ops.push(op); break; }
                    if (t[2]) _.ops.pop();
                    _.trys.pop(); continue;
            }
            op = body.call(thisArg, _);
        } catch (e) { op = [6, e]; y = 0; } finally { f = t = 0; }
        if (op[0] & 5) throw op[1]; return { value: op[0] ? op[1] : void 0, done: true };
    }
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
exports.__esModule = true;
var express_1 = __importDefault(require("express"));
var nanoid_1 = require("nanoid");
var cors = require("cors");
var bodyParser = require("body-parser");
var mongoose = require("mongoose");
var ShortUrl = require("./models/shortUrl");
var app = (0, express_1["default"])();
mongoose.connect("mongodb://127.0.0.1/urlShortener", {
    useNewUrlParser: true,
    useUnifiedTopology: true
});
app.use(cors());
app.use(express_1["default"].urlencoded({ extended: false }));
app.use(bodyParser.json());
app.post("/w/shrink", function (req, res) { return __awaiter(void 0, void 0, void 0, function () {
    var data, RETRY_MAX, tryTimes, url, temp, e_1;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                data = {
                    fullUrl: req.body.fullUrl,
                    shortUrl: ""
                };
                RETRY_MAX = 3;
                tryTimes = 0;
                console.log(44);
                _a.label = 1;
            case 1:
                if (!(tryTimes < RETRY_MAX)) return [3 /*break*/, 3];
                url = (0, nanoid_1.nanoid)(8);
                console.log('url', url);
                return [4 /*yield*/, ShortUrl.findOne({ shortUrl: url })];
            case 2:
                temp = _a.sent();
                if (temp === null) {
                    data.shortUrl = url;
                    return [3 /*break*/, 3];
                }
                tryTimes++;
                return [3 /*break*/, 1];
            case 3:
                if (!data.shortUrl) {
                    res.status(500).send({ error: "short url generate failed" });
                    return [2 /*return*/];
                }
                _a.label = 4;
            case 4:
                _a.trys.push([4, 6, , 7]);
                return [4 /*yield*/, ShortUrl.create(data)];
            case 5:
                _a.sent();
                res.send(data);
                return [3 /*break*/, 7];
            case 6:
                e_1 = _a.sent();
                res.status(500).send({ error: "system error" });
                return [3 /*break*/, 7];
            case 7: return [2 /*return*/];
        }
    });
}); });
app.get("/r/search", function (req, res) { return __awaiter(void 0, void 0, void 0, function () {
    var urlData, e_2, urlList, e_3;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                if (!req.query.shortUrl) return [3 /*break*/, 5];
                _a.label = 1;
            case 1:
                _a.trys.push([1, 3, , 4]);
                return [4 /*yield*/, ShortUrl.findOne({ shortUrl: req.query.shortUrl })];
            case 2:
                urlData = _a.sent();
                if (urlData == null)
                    return [2 /*return*/, res.send([])];
                res.send([urlData]);
                return [3 /*break*/, 4];
            case 3:
                e_2 = _a.sent();
                res.status(500).send({ error: "system error" });
                return [3 /*break*/, 4];
            case 4: return [3 /*break*/, 8];
            case 5:
                _a.trys.push([5, 7, , 8]);
                return [4 /*yield*/, ShortUrl.find()];
            case 6:
                urlList = _a.sent();
                res.send(urlList);
                return [3 /*break*/, 8];
            case 7:
                e_3 = _a.sent();
                res.status(500).send({ error: "system error" });
                return [3 /*break*/, 8];
            case 8: return [2 /*return*/];
        }
    });
}); });
app.listen(process.env.PORT || 5000);
module.exports = app;
// mongod --config /usr/local/etc/mongod.conf
