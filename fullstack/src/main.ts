import crypto from "node:crypto";
import base58 from "bs58";
import express from "express";
import * as exp from "drizzle-orm/expressions";

import * as db from "./db";

const app = express();
app.use(express.static("fe/dist"));

app.use(express.json());
app.post("/api/long_to_short", (req, res) => {
  const long = req.body?.long;
  if (!long || typeof long !== "string") {
    res.status(400).json({ code: 1, msg: "", data: null });
    return;
  }
  db.conn.transaction(
    (tx) => {
      const short = base58.encode(crypto.randomBytes(6)).slice(0, 8);
      const data = { short, long };
      const result = tx.insert(db.short_urls).values(data).run();
      if (result.changes) {
        res.json({ code: 0, msg: "", data });
        return;
      }
      res.status(500).json({ code: 1, msg: "", data: null });
    },
    { behavior: "immediate" }
  );
});

app.get("/api/short_to_long", (req, res) => {
  const short = req.query?.short;
  if (!short || typeof short !== "string") {
    res.status(400).json({ code: 1, msg: "", data: null });
    return;
  }
  const data = db.conn
    .select()
    .from(db.short_urls)
    .where(exp.eq(db.short_urls.short, short))
    .get();
  res.json({ code: 0, msg: "", data });
});

app.get("/:short", (req, res) => {
  const short = req.params.short;
  const data = db.conn
    .select()
    .from(db.short_urls)
    .where(exp.eq(db.short_urls.short, short))
    .get();
  res.redirect(data.long);
});

app.listen(8080, () => {
  console.log("server is listening on :8080");
});
