
import "reflect-metadata";
import { Statistics } from "../../../src/interfaces/statistics.interface";
import { StatisticsModel } from "../../../src/models/statistics.model";
import statisticsService from "../../../src/services/statistics.service";
import mongoService from '../../../src/services/mongo.service';

describe("Service: StatisticsService", () => {
  
  beforeAll(async ()=> {
    await mongoService.connect();
  });

  afterAll(async()=> {
    await StatisticsModel.deleteMany({});
    await mongoService.disconnect();
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  describe("createByOption", () => {
    const mockOption = {
      urlUid: "mockUrlUid",
      ip: "mockIp",
      referer: "mockRefer",
      userAgent: "mockUserAgent",
      language: "mockLanguage",
      accept: "mockAccept",
    };

    it("should create a new statistics record with the given option", async () => {

      const result = await statisticsService.createByOption(mockOption as Statistics);
      expect(result.urlUid).toEqual(mockOption.urlUid);
    });

    it("should throw an error if creating the statistics record fails", async () => {
      const mockError = new Error("Failed to create statistics record");
      jest.spyOn(StatisticsModel, 'create').mockRejectedValueOnce(mockError);

      await expect(statisticsService.createByOption(mockOption as Statistics)).rejects.toThrow(mockError);

      expect(StatisticsModel.create).toBeCalledWith(mockOption);
    });
  });
});
