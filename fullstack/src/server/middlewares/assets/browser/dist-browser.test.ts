import { afterEach, beforeEach, describe, expect, it } from "@jest/globals";
import express, { type Express } from "express";
import fs from "fs";
import path from "path";
import supertest, { type SuperTest, type Test } from "supertest";
import { assets } from "./dist-browser.js";

describe("Server assets", () => {
  let app: Express;
  let request: SuperTest<Test>;
  let filePath: string;
  let fileName: string;
  let fileContents: string;

  beforeEach(() => {
    app = express();
    app.use(assets());
    request = supertest(app);

    fileName = "test.html";
    fileContents = "Hello Node.js!";
    const folderPath = path.join("dist", "browser");
    filePath = path.join(folderPath, fileName);
    fs.mkdirSync(folderPath, { recursive: true });
    fs.writeFileSync(filePath, fileContents);
  });

  afterEach(() => {
    fs.unlinkSync(filePath);
  });

  it("Returns generated file", async () => {
    const response = await request.get(`/${fileName}`);

    expect(response.statusCode).toBe(200);

    expect(response.headers["content-type"]).toBe("text/html; charset=utf-8");

    expect(response.text).toBe(fileContents);
  });
});
