"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const express_1 = __importDefault(require("express"));
// import { connectDB } from './config/db'
const index_1 = __importDefault(require("./routes/index"));
const url_1 = __importDefault(require("./routes/url"));
const app = (0, express_1.default)();
// connectDB();
app.use('/', index_1.default);
app.use('/api/url', url_1.default);
const port = 5000;
app.listen(port, () => {
    console.log(`Server running on port ${port}`);
});
