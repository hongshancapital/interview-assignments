import urlUnshortener from "../../api/unshorten";

jest.mock("../../database/mysql", () => {
  return {
    findOrignalURLByShort: jest
      .fn()
      .mockImplementationOnce(() => Promise.resolve("https://www.google.hk"))
      .mockImplementationOnce(() =>
        Promise.resolve("Failed to get the orignal URL from db."),
      )
      .mockImplementationOnce(() => Promise.resolve("")),
  };
});

describe("Unit tests of <Unshortener>", () => {
  describe("Test function <urlUnshortener>", () => {
    test("Normal Request", async () => {
      const result = await urlUnshortener("https://bitly.co/6c");
      expect(result).toHaveProperty("success", true);
      expect(result).toHaveProperty("url", "https://www.google.hk");
      expect(result).toHaveProperty("msg", "orignal url found");
    });

    test("Something failed on database side", (done) => {
      urlUnshortener("https://bitly.co/6c").catch((err) => {
        expect(err).toHaveProperty("success", false);
        expect(err).toHaveProperty("url", "https://bitly.co/6c");
        expect(err).toHaveProperty("msg");
        expect(err.msg).toMatch("Failed to get the orignal URL from db.");
        done();
      });
    });

    test("Original url not found", async () => {
      const result = await urlUnshortener("https://bitly.co/6c");
      expect(result).toHaveProperty("success", false);
      expect(result).toHaveProperty("url", "https://bitly.co/6c");
      expect(result).toHaveProperty("msg", "orignal url not found");
    });
  });
});
