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
        while (_) try {
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
exports.__esModule = true;
var Koa = require("koa");
var Router = require("koa-router");
var KoaBodyparser = require("koa-bodyparser");
var constant_1 = require("./constant");
var service_1 = require("./service");
var app = new Koa();
var router = new Router();
router.get('/save', function (ctx) { return __awaiter(void 0, void 0, void 0, function () {
    var _url, url, id;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                _url = ctx.request.query.url;
                console.log('[_url]', _url);
                url = Array.isArray(_url) ? _url[0] : _url;
                if (!url)
                    return [2 /*return*/, ctx.body = {
                            code: constant_1.CODE.ERROR.QUERY_ERROR,
                            message: constant_1.MESSAGE.QUERY_ERROR
                        }];
                url = decodeURIComponent(url);
                return [4 /*yield*/, (0, service_1.saveUrlAndReturnShortId)(url)];
            case 1:
                id = _a.sent();
                return [2 /*return*/, ctx.body = {
                        code: constant_1.CODE.SUCCESS,
                        message: constant_1.MESSAGE.SUCCESS,
                        data: {
                            id: id
                        }
                    }];
        }
    });
}); });
router.get('/get', function (ctx) { return __awaiter(void 0, void 0, void 0, function () {
    var _id, id, url;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                _id = ctx.request.query.id;
                id = Array.isArray(_id) ? _id[0] : _id;
                console.log('[_id]', id);
                if (!id)
                    return [2 /*return*/, ctx.body = {
                            code: constant_1.CODE.ERROR.QUERY_ERROR,
                            message: constant_1.MESSAGE.QUERY_ERROR
                        }];
                return [4 /*yield*/, (0, service_1.getUrlWithShortId)(id)];
            case 1:
                url = _a.sent();
                if (!url)
                    return [2 /*return*/, ctx.body = {
                            code: constant_1.CODE.ERROR.URL_INVALID,
                            message: constant_1.MESSAGE.URL_INVALID
                        }];
                return [2 /*return*/, ctx.body = {
                        code: constant_1.CODE.SUCCESS,
                        message: constant_1.MESSAGE.SUCCESS,
                        data: {
                            url: url
                        }
                    }];
        }
    });
}); });
router.get('/r/:id', function (ctx) { return __awaiter(void 0, void 0, void 0, function () {
    var id, url;
    return __generator(this, function (_a) {
        switch (_a.label) {
            case 0:
                id = ctx.params.id;
                if (!id)
                    return [2 /*return*/, {
                            code: constant_1.CODE.ERROR.URL_INVALID,
                            message: constant_1.MESSAGE.URL_INVALID
                        }];
                return [4 /*yield*/, (0, service_1.getUrlWithShortId)(id)];
            case 1:
                url = _a.sent();
                if (!url)
                    return [2 /*return*/, {
                            code: constant_1.CODE.ERROR.URL_INVALID,
                            message: constant_1.MESSAGE.URL_INVALID
                        }];
                ctx.redirect(url);
                return [2 /*return*/];
        }
    });
}); });
app.use(KoaBodyparser());
app.use(router.routes());
app.listen(3000);
