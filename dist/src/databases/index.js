"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const mongoose_1 = require("mongoose");
async function connectDatabase(uri) {
    mongoose_1.connection.on('close', () => console.log('Database connection closed.'));
    return mongoose_1.connect(uri, { useNewUrlParser: true, useUnifiedTopology: true });
}
exports.default = connectDatabase;
//# sourceMappingURL=index.js.map