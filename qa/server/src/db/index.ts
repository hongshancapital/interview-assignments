import sqlite3 from "sqlite3";

const DBSOURCE = "db.sqlite";

let db = new sqlite3.Database(DBSOURCE, (err) => {
  if (err) {
    // Cannot open database
    console.error(err.message);
    throw err;
  } else {
    console.log("Connected to the SQLite database.");
    db.run(
      `CREATE TABLE toolbox_prefs (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name text, 
            tools text,
            CONSTRAINT name_unique UNIQUE (name)
            )`,
      (err) => {
        console.log(err);
      }
    );
    db.run(
      `create table cloud_short_link (
          id          INTEGER PRIMARY KEY,
          lurl        varchar(128) default '' not null,
          short_id    varchar(128) default '' not null unique,
          app_id      varchar(32)  default '' not null,
          expire_time int(11)      default 0 not null,
          create_time int(11)      default 0 not null
      )`,
      (err) => {
        console.log(err);
      }
    );
  }
});

export default db;
