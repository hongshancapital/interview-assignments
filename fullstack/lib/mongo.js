"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const mongoose_1 = __importDefault(require("mongoose"));
const index_1 = __importDefault(require("./config/index"));
const dbURI = index_1.default.DB;
const { Schema } = mongoose_1.default;
mongoose_1.default.connect(dbURI);
const db = mongoose_1.default.connection;
const SlinkSchema = new Schema({
    id: String,
    url: String,
});
mongoose_1.default.model('Slink', SlinkSchema);
db.on('error', () => {
    throw new Error(`unable to connect to database at ${dbURI}`);
});
