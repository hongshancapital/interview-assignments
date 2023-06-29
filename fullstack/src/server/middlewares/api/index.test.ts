import { beforeEach, describe, expect, it, jest } from "@jest/globals";
import express, { type Express, type Request, type Response } from "express";
import supertest, { type SuperTest, type Test } from "supertest";

const mockCreateShortLink = jest.fn((req: Request, res: Response) =>
  res.sendStatus(204)
);
const mockGetOriginLinkById = jest.fn((req: Request, res: Response) =>
  res.sendStatus(204)
);

jest.unstable_mockModule("./path/createShortLink.js", () => {
  return {
    createShortLink: mockCreateShortLink,
  };
});

jest.unstable_mockModule("./path/getOriginLinkById.js", () => {
  return {
    getOriginLinkById: mockGetOriginLinkById,
  };
});

describe("Server assets", () => {
  let app: Express;
  let request: SuperTest<Test>;

  beforeEach(async () => {
    const { api } = await import("./index.js");
    app = express();
    app.use(api());
    request = supertest(app);
  });

  it("healthy check", async () => {
    const response = await request.get("/");

    expect(response.statusCode).toBe(204);
  });

  it("request createShortLink endpoint", async () => {
    const response = await request.post("/createShortLink");

    expect(mockCreateShortLink).toBeCalledTimes(1);
    expect(response.statusCode).toBe(204);
  });

  it("request getOriginLinkById endpoint", async () => {
    const response = await request.get("/go/1");

    expect(mockGetOriginLinkById).toBeCalledTimes(1);
    expect(response.statusCode).toBe(204);
  });

  it("returns 404 for unmatched api", async () => {
    const response = await request.get("/lorem");

    expect(response.statusCode).toBe(404);
  });
});
