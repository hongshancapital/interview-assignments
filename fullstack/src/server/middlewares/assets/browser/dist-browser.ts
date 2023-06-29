import express from "express";
import mime from "mime/lite.js";
import path from "path";

const relativeDistDirPath = path.join("dist", "browser");
const rootIndexFilePath = path.join(relativeDistDirPath, "index.html");

export function assets() {
  return [serveDist, serveDefaultPage];
}

const serveDist = express.static(relativeDistDirPath, {
  maxAge: 60000,
  setHeaders: (res, path) => {
    const contentType = mime.getType(path);
    res.set("Content-Type", contentType || "text/plain");
  },
});

const serveDefaultPage: express.RequestHandler = (req, res) => {
  res.sendFile(path.resolve(rootIndexFilePath));
};
