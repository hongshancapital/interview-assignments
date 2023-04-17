"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.default = (opts) => {
    return async (ctx, next) => {
        try {
            await next();
        }
        catch (e) {
            switch (e.status) {
                case 401:
                    ctx.body = { error: e.originalError ? e.originalError.message : e.message };
                    break;
                default:
                    ctx.body = e.stack || e.message;
            }
        }
    };
};
//# sourceMappingURL=error.js.map