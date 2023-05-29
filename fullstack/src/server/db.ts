import pg from "pg";

export const UNIQUE_VIOLATION = "23505";

const db = new pg.Client({
    /* istanbul ignore next */
    database: process.env.NODE_ENV === "test" ? "interview_database_test" : "interview_database",
    host: "localhost",
    user: "postgres",
    password: "localpass",
});
let _initialized = false;

export async function init() {
    if (_initialized) {
        /* istanbul ignore next */
        throw new Error("Can only initialize once");
    }
    await db.connect();
    _initialized = true;
}

export async function close() {
    await db.end();
}

export default db;
