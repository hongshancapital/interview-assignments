import urlShortener from "../../api/shorten";

jest.mock("../../database/mysql", () => {
  return {
    findShortenedURLByOrignal: jest
      .fn()
      .mockResolvedValueOnce("Failed to get the shortened URL from db.")
      .mockResolvedValueOnce("")
      .mockResolvedValueOnce("")
      .mockResolvedValueOnce("https://bitly.com/as3")
      .mockResolvedValueOnce(""),
    findMaxIdInTinyURL: jest
      .fn()
      .mockResolvedValueOnce("Failed to get the max id of table tiny_table.")
      .mockResolvedValueOnce(1)
      .mockResolvedValueOnce(1)
      .mockResolvedValueOnce(1),
    insertTinyURL: jest
      .fn()
      .mockResolvedValueOnce("Failed to insert the shortened URL into db.")
      .mockResolvedValueOnce([]),
  };
});

describe("Unit tests of <Shortener>", () => {
  describe("Test function <urlShortener>", () => {
    test("Db failed when check if the original url has been shortened ever", (done) => {
      urlShortener("https://www.baidu.com").catch((err) => {
        expect(err).toHaveProperty("success", false);
        expect(err).toHaveProperty("url", "https://www.baidu.com");
        expect(err).toHaveProperty("msg");
        expect(err.msg).toMatch("Failed to get the shortened URL from db.");
        done();
      });
    });

    test("Db failed when get max id", (done) => {
      urlShortener("https://www.baidu.com").catch((err) => {
        expect(err).toHaveProperty("success", false);
        expect(err).toHaveProperty("url", "https://www.baidu.com");
        expect(err).toHaveProperty("msg");
        expect(err.msg).toMatch(
          "Failed to get the max id of table tiny_table.",
        );
        done();
      });
    });

    test("Db failed when save the shortened result", (done) => {
      urlShortener("https://www.baidu.com").catch((err) => {
        expect(err).toHaveProperty("success", false);
        expect(err).toHaveProperty("url", "https://www.baidu.com");
        expect(err).toHaveProperty("msg");
        expect(err.msg).toMatch("Failed to insert the shortened URL into db.");
        done();
      });
    });

    test("Normal Request, the original url has been shortened before", (done) => {
      urlShortener("https://www.baidu.com").then((res) => {
        expect(res).toHaveProperty("success", false);
        expect(res).toHaveProperty("url", "https://bitly.com/as3");
        expect(res).toHaveProperty(
          "msg",
          "original url has been created before",
        );
        done();
      });
    });

    test("Normal Request, the original url has not been shortened before", (done) => {
      urlShortener("https://www.baidu.com").then((res) => {
        expect(res).toHaveProperty("success", true);
        expect(res).toHaveProperty("url", "https://gitly.io/2");
        expect(res).toHaveProperty("msg", "shortened url created");
        done();
      });
    });
  });
});
