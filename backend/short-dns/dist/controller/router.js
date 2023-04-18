"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.prototype.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
var express_promise_router_1 = __importDefault(require("express-promise-router"));
var router_middleware_1 = require("../middleware/router-middleware");
var validation_1 = require("../middleware/validation");
var shortDNS = __importStar(require("./dns-controller"));
var rest_valid_1 = require("../middleware/rest-valid");
var dns_validation_1 = require("./dns-validation");
var router = (0, express_promise_router_1.default)();
var r = new router_middleware_1.APIRouter(router);
r.route('POST', '/', (0, validation_1.api)({ body: dns_validation_1.LongDnsDTOSchema, query: rest_valid_1.ensureEmpty, ret: dns_validation_1.DnsDTOSchema }, shortDNS.getShortDNS));
r.route('GET', '/:shortDns', (0, validation_1.api)({ body: rest_valid_1.ensureEmpty, query: rest_valid_1.ensureEmpty, ret: dns_validation_1.DnsDTOSchema }, shortDNS.getLongDNS));
exports.default = router;
