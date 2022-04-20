import {Client, QueryResult} from "pg";
import {BASE_URL, MAINFRAME_CODE} from "../server";
import {GetNextShorterByCurrent} from "./generate_shorter";
import {ShortLongMap} from "../types";

const POSTGRES_HOST = process.env.POSTGRES_HOST || "localhost";
const POSTGRES_USER = process.env.POSTGRES_USER || "interview-assignment";
const POSTGRES_PASSWORD = process.env.POSTGRES_PASSWORD || "interview-assignment";
const POSTGRES_DATABASE = process.env.POSTGRES_DATABASE || "interview-assignment";

export const PostgresClient = new Client({
    host: POSTGRES_HOST,
    user: POSTGRES_USER,
    password: POSTGRES_PASSWORD,
    database: POSTGRES_DATABASE,
});

(async () => {
    await PostgresClient.connect();
})();


function getFirstRow(result: QueryResult) {
    let ret: ShortLongMap | undefined;

    if (result.rowCount > 0) {
        ret = {} as ShortLongMap;
        ret.shortDomain =  BASE_URL + result.rows[0].short_domain;
        ret.longDomain = result.rows[0].long_domain;
    }

    return ret;
}

export async function GetDbByLongDomain(longName: string) {
    const result = await PostgresClient.query(
        `SELECT short_domain, long_domain FROM short_long_map 
              WHERE long_domain = $1`,
        [longName]);

    return getFirstRow(result);
}

export async function GetDbByShortDomain(shortName: string) {
    const result = await PostgresClient.query(
        `SELECT short_domain, long_domain FROM short_long_map 
              WHERE short_domain = $1`,
        [shortName]);

    return getFirstRow(result);
}

export async function GetMainFrameLastShort() {
    const result = await PostgresClient.query(
        `SELECT short_domain FROM short_long_map 
              WHERE short_domain LIKE $1
              ORDER BY create_time DESC
              LIMIT 1`,
        [MAINFRAME_CODE + "%"]);

    let currentName = "";
    if (result.rowCount > 0) {
        currentName = result.rows[0].short_name;
    }

    return GetNextShorterByCurrent(currentName);
}

export async function SaveMapToDb(stored: ShortLongMap) {
    await PostgresClient.query(
        `INSERT INTO short_long_map (short_domain, long_domain) VALUES ($1, $2)`,
        [stored.shortDomain.substring(BASE_URL.length), stored.longDomain]
    );
}

export async function ClearDb() {
    await PostgresClient.query("DELETE FROM short_long_map");
}