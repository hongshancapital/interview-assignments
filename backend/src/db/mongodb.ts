import mongoose from 'mongoose';
const dbAddress = process.env.DB_URL || 'mongodb://127.0.0.1:27017/test';

export default new class Mongodb {
    constructor() {
        //
    }

    async connect() {
        await mongoose.connect(dbAddress);
    }

    get server() {
        return mongoose.connection;
    }

    async close() {
        if (this.isOk()) {
            await mongoose.connection.close();
        }
    }

    isOk() {
        return mongoose.connection.readyState === 1;
    }
};
