const fs = require("node:fs");
const path = require("node:path");

const Database = require("better-sqlite3");

const sqlite = new Database("sqlite.db");

console.log(path.join(__dirname, "../migrations"));

fs.readdirSync(path.join(__dirname, "../migrations"))
  .filter((it) => it.endsWith(".sql"))
  .map((it) => {
    return new TextDecoder().decode(
      fs.readFileSync(path.join(__dirname, "../migrations", it))
    );
  })
  .forEach((it) => {
    sqlite.exec(it);
  });
