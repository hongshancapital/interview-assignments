import { Test, TestingModule } from "@nestjs/testing";

import { AppController } from "./app.controller";
import { AppService } from "./app.service";

import { Shorten } from "./entity/shorten";
import { ShortenDto } from "./dto/ShortenDto";
import { TrackLink } from "./entity/tracklink";
import { TypeOrmModule } from "@nestjs/typeorm";

describe("AppController", () => {
  let appController: AppController;
  let appService: AppService;

  beforeEach(async () => {
    jest.restoreAllMocks();
    const app: TestingModule = await Test.createTestingModule({
      controllers: [AppController],
      providers: [AppService],
    }).compile();

    appController = app.get<AppController>(AppController);
    appService = app.get<AppService>(AppService);
  });

  describe("Get shortened link", () => {
    it('should return "Shorten" object', async () => {
      const shortenDto = new ShortenDto();
      shortenDto.link = "https://docs.nestjs.com/";

      const shortenEntity = new Shorten();
      shortenEntity.link = "https://docs.nestjs.com/";
      shortenEntity.shortened = "M3ZQ0FX8";
      shortenEntity.createdAt = "2020-11-22T15:50:04.766Z";
      shortenEntity.updatedAt = "2020-11-22T15:50:04.855Z";

      const result = new Promise<Shorten>((resolve) => {
        resolve(shortenEntity);
      });

      jest
        .spyOn(appService, "shortenerLink")
        .mockImplementation(async () => result);

      expect(await appController.getShortened(shortenDto)).toBe(shortenEntity);
    });
  });

  describe("Get original link", () => {
    it("should return original link", async () => {
      const result = "https://docs.nestjs.com/";

      jest
        .spyOn(appService, "retrieveShortened")
        .mockImplementation(async () => result);

      jest
        .spyOn(appService, "trackShortened")
        .mockImplementation(async () => {});

      expect(await appController.getLink("M3ZQ0FX8", null)).toBe(result);
    });
  });
});
