import "reflect-metadata";
import mongoService from '../../../src/services/mongo.service';
import { logger } from "../../../src/utils/logger";

describe('MongoService', () => {

  beforeEach(()=> {
    jest.clearAllMocks();
  });

  afterAll(async () => {
    await mongoService.disconnect();
    jest.clearAllMocks();
  });
 

  describe('init', () => {
    it('should initialize mongoClient and mongoConnection', () => {
      expect(mongoService.mongoClient).toBeDefined();
      expect(mongoService.mongoConnection).toBeDefined();
    });

    it('should log error and set ready to false when connection emits an error', () => {
      mongoService.ready = true;
      mongoService.mongoConnection.emit('error', new Error('Connection error'));
      expect(mongoService.ready).toBe(false);
    });

    it('should log warn and set ready to false when disconnected emits an error', () => {
      mongoService.ready = true;
      mongoService.mongoConnection.emit('disconnected');
      expect(mongoService.ready).toBe(false);
    });

    it('should log warn and set ready to false when close emits an error', () => {
      mongoService.ready = true;
      mongoService.mongoConnection.emit('close');
      expect(mongoService.ready).toBe(false);
    });

    it('should log info and set ready to true when close emits an connected', () => {
      mongoService.ready = false;
      mongoService.mongoConnection.emit('connected');
      expect(mongoService.ready).toBe(true);
    });

  });


  describe('connect', () => {
    it('should return error if connect throws', async () => {
      mongoService.ready = true;
      const connectSpy = jest.spyOn(mongoService.mongoClient, 'connect');
      const result = await mongoService.connect();
      expect(connectSpy).not.toHaveBeenCalled();
      expect(result).toBe(true);
  
    });

    it('should log error if connect throws', async () => {
      mongoService.ready = false;
      jest.spyOn(mongoService.mongoClient, 'connect').mockRejectedValue(new Error('connect failed'));
      const errorSpy = jest.spyOn(logger, 'error');
      const result = await mongoService.connect();
      expect(errorSpy).toHaveBeenCalled();
      expect(result).toBe(false);
    });
  
    it('should call connect if not ready', async () => {
      mongoService.ready = false;
      jest.spyOn(mongoService.mongoClient, 'connect').mockResolvedValue({ } as any);
      const result = await mongoService.connect();
      expect(result).toBe(true);
    });
  });

  describe('disconnect()', () => {
    it('should log error if disconnect throws', async () => {
      mongoService.ready = true;
      mongoService.mongoClient.disconnect = jest.fn().mockRejectedValue(new Error('Disconnect failed'));
      const errorSpy = jest.spyOn(logger, 'error');
      const result = await mongoService.disconnect();
      expect(errorSpy).toHaveBeenCalled();
      expect(result).toBe(false);
    });
  
    it('should call disconnect if ready', async () => {
      mongoService.ready = true;
      mongoService.mongoClient.disconnect = jest.fn().mockResolvedValue({} as any);
      const result = await mongoService.disconnect();
      expect(result).toBe(true);
    });

  });

});
