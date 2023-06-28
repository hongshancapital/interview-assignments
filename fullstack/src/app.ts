import { json } from "body-parser";
import express from "express";
import helmet from "helmet";
import { decodeHandler } from "./handlers/decode";
import { encodeHandler } from "./handlers/encode";

const app = express();

app.use(helmet());
app.use(json());

app
  .post("/encode", encodeHandler)
  .post("/decode", decodeHandler)
  .get("*", (_req, res) => {
    res.status(404).json({ message: 404 });
  });

export { app };
