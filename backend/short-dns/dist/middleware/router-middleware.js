"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.APIRouter = void 0;
var express_1 = require("express");
var http_error_1 = require("./http-error");
var errors_1 = require("../types/errors");
var APIRouter = /** @class */ (function () {
    function APIRouter(express) {
        this.expressRouter = express ? express : (0, express_1.Router)();
    }
    APIRouter.prototype.route = function (method, rt, ctrl) {
        var statusOK = method === 'POST' ? 201 : 200;
        var expressCtrl = function (req, res, done) {
            res.status(statusOK);
            ctrl({ request: req }, res)
                .then(function () { done(); })
                .catch(function (err) {
                if (err instanceof http_error_1.HttpError) {
                    res.status(err.status).json({
                        code: err.code,
                    });
                    return;
                }
                else {
                    res.status(500).json({ code: errors_1.HttpErrorCode.systemError });
                    return;
                }
            });
        };
        switch (method) {
            case 'GET':
                this.expressRouter.get(rt, expressCtrl);
                break;
            case 'POST':
                this.expressRouter.post(rt, expressCtrl);
                break;
            default: return;
        }
    };
    return APIRouter;
}());
exports.APIRouter = APIRouter;
