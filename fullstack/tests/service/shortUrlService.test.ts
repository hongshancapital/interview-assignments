import ShortUrlService from "../../src/service/shorUrlService";
import MemoryCache from "../../src/service/memoryCache";

describe("shortUrlService", () => {
  const service = new ShortUrlService();
  it("generator", () => {
    const mockCahche = {
      getValue: jest.fn().mockReturnValue(undefined),
      setValue: jest.fn(),
    };
    MemoryCache.getInstance = jest.fn().mockReturnValue(mockCahche);
    const short = service.generalSortUrl("url");
    expect(mockCahche.getValue).toHaveBeenCalledTimes(1);
    expect(mockCahche.setValue).toHaveBeenCalledWith(short, 'url');
  });

  it("conflic retry", () => {
    const mockCahche = {
      getValue: jest.fn().mockReturnValueOnce("exists").mockReturnValueOnce("exists").mockReturnValueOnce(undefined),
      setValue: jest.fn(),
    };
    MemoryCache.getInstance = jest.fn().mockReturnValue(mockCahche);
    const short = service.generalSortUrl("url");
    expect(mockCahche.getValue).toHaveBeenCalledTimes(3);
    expect(mockCahche.setValue).toHaveBeenCalledWith(short, 'url');
  });

  it("get origin", () => {
    const mockCahche = {
      getValue: jest.fn().mockReturnValue('mock_url'),
    };
    MemoryCache.getInstance = jest.fn().mockReturnValue(mockCahche);
    const origin = service.getOriginalUrl("shorten");
    expect(mockCahche.getValue).toHaveBeenCalledWith("shorten");
    expect(origin).toBe('mock_url');
  });

});