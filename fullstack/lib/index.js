"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const app_1 = __importDefault(require("./app"));
const index_1 = __importDefault(require("./config/index"));
const port = process.env.PORT || index_1.default.PORT;
app_1.default.listen(port, () => {
    console.log('Server is running at http://127.0.0.1:' + port);
});
