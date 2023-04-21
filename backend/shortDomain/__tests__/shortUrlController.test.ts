import request from "supertest";
import app from "../src/app";
import mongoose from "mongoose";
import ShortUrl, { IShortUrl } from "../src/models/shortUrl";

describe("Short URL API", () => {
  beforeAll(async () => {
    await mongoose.connect("mongodb://10.0.68.132:27017/test");
  });

  afterEach(async () => {
    await ShortUrl.deleteMany({});
  });

  afterAll(async () => {
    await mongoose.connection.close();
  });

  test("Create a short URL", async () => {
    const longUrl = "https://www.example.com";

    const response = await request(app).post("/shorturl").send({ longUrl });

    expect(response.status).toBe(201);
    expect(response.body.shortUrl).toMatch(/http:\/\/your-domain.com\/\w{1,8}/);

    const shortUrl: IShortUrl | null = await ShortUrl.findOne({ longUrl });
    expect(shortUrl).not.toBeNull();
  });

  test("Retrieve the long URL by short code", async () => {
    const longUrl = "https://www.example.com";
    const shortCode = "abcdefgh";
    const shortUrl = new ShortUrl({ longUrl, shortCode });
    await shortUrl.save();

    const response = await request(app).get(`/shorturl/${shortCode}`);

    expect(response.status).toBe(200);
    expect(response.body.longUrl).toEqual(longUrl);
  });

  test("Short code not found", async () => {
    const shortCode = "abcdefgh";

    const response = await request(app).get(`/shorturl/${shortCode}`);

    expect(response.status).toBe(404);
    expect(response.body.error).toEqual("Short URL not found.");
  });
});
