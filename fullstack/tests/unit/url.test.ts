// eslint-disable-next-line node/no-unpublished-import
import UrlShorten from "../../src/server/models/urls";
import * as mockingoose from "mockingoose";

jest.setTimeout(5000);

describe("url model mocking test", () => {
  beforeEach(() => {
    mockingoose.resetAll();
    jest.clearAllMocks();
  });

  describe("explicit tests", () => {
    it("should be a validate url object", async () => {
      const url = new UrlShorten({
        rawUrl: "http://www.domain.com/api/test/123",
        urlCode: "fuir90rf",
      });

      await url.validate();
      expect(url.toObject()).toHaveProperty("rawUrl");
    });

    it("should return a matched object", async () => {
      mockingoose(UrlShorten).toReturn([{ urlCode: "fuir90rf" }]);

      const result = await UrlShorten.find().lean();
      expect(result[0]).toMatchObject({ urlCode: "fuir90rf" });
    });

    it("should match a given url object", async () => {
      const url = {
        createdAt: "2023/2/15 09:17:17",
        rawUrl: "https://www.sequoiacap.cn/team/",
        shortenUrl: "https://www.sequoiacap.cn/kRsaqCF1",
        urlCode: "kRsaqCF1",
        _id: "63ec329dd9f55107e6d6bd08",
      };
      mockingoose(UrlShorten).toReturn(url, "findOne");

      return UrlShorten.findById({ _id: "63ec329dd9f55107e6d6bd08" }).then(
        (doc) => {
          expect(JSON.parse(JSON.stringify(doc))).toMatchObject(url);
        }
      );
    });

    it("should return a result that is an instance of given model", async () => {
      const returnMock = jest.fn().mockReturnValue({ urlCode: "kRsaqCF1" });
      mockingoose(UrlShorten).toReturn(returnMock, "findOne");

      const result = await UrlShorten.findOne();
      expect(result.toObject()).toHaveProperty("urlCode");
      expect(result).toBeInstanceOf(UrlShorten);
    });

    it('should return true for exists urlCode', async () => {
        mockingoose(UrlShorten).toReturn({}, 'findOne');

        const result = await UrlShorten.exists({ urlCode: "kRsaqCF1" });
        expect(result).toBeTruthy();
      });
  });
});
