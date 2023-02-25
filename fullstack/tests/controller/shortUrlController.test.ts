import { Request, Response } from 'express';
import ShortUrlController from '../../src/controller/shortUrlController';
import ShortUrlService from '../../src/service/shorUrlService';

const mockShortUrlService: ShortUrlService = {
  generalSortUrl: (url:string):string => {
    return 'mock_short';
  },
  getOriginalUrl: (key:string):string => {
    return 'mock_origin';
  },
}

// jest.mock('../../src/services/shortUrlService', () => ({
//   __esModule: true,
//   default: mockShortUrlService,
// }));
jest.mock('../../src/service/shorUrlService', () => jest.fn().mockImplementation(() => mockShortUrlService));

describe('shortUrlController', () => {
  it("shorten url", () => {
    const shortUrlController = new ShortUrlController();
    const mockReq: Request = <Request>{
      body: {
        url: 'sample_url'
      }
    }
    const generalSortUrl = jest.spyOn(mockShortUrlService,  'generalSortUrl');
    const mockNext = jest.fn();
    shortUrlController.post(mockReq, <Response>{}, mockNext);
    expect(generalSortUrl).toHaveBeenCalledWith('sample_url');
    expect(mockNext).toHaveBeenCalledWith( {shorten: 'mock_short'});
  });

  it("shorten Error", () => {
    const shortUrlController = new ShortUrlController();
    const mockReq: Request = <Request>{
      body: {}
    }
    const generalSortUrl = jest.spyOn(mockShortUrlService,  'generalSortUrl');
    const mockNext = jest.fn();
    shortUrlController.post(mockReq, <Response>{}, mockNext);
    expect(generalSortUrl).not.toHaveBeenCalled();
    expect(mockNext).toHaveBeenCalled();

  });

  it("get origin url", () => {
    const shortUrlController = new ShortUrlController();
    const mockReq: Request = <Request>{query:{}};
    mockReq.query = {shorten: 'shorten_url'};
    const getOriginalUrl = jest.spyOn(mockShortUrlService,  'getOriginalUrl');
    const mockNext = jest.fn();
    shortUrlController.get(mockReq, <Response>{}, mockNext);
    expect(getOriginalUrl).toHaveBeenCalledWith('shorten_url');
    expect(mockNext).toHaveBeenCalledWith( {url: 'mock_origin'});
  });

  it("get origin Error", () => {
    const shortUrlController = new ShortUrlController();
    const mockReq: Request = <Request>{query:{}};
    const getOriginalUrl = jest.spyOn(mockShortUrlService,  'getOriginalUrl');
    const mockNext = jest.fn();
    shortUrlController.get(mockReq, <Response>{}, mockNext);
    expect(getOriginalUrl).not.toHaveBeenCalled();
    expect(mockNext).toHaveBeenCalled();

  });
});