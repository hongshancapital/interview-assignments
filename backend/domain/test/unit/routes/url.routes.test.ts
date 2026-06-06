import "reflect-metadata";
import urlRoutes from '../../../src/routes/url.route';

jest.mock('../../../src/services/redis.service');

describe('Routes: urlRoutes', () => {

  afterAll(() => {
    jest.resetAllMocks();
  });

  it("should call urlController.create method with auth middleware when POST /url is called", () => {
    const postUrlRoute = urlRoutes.getRouter().stack.find(
      (s) => s.route && s.route.path === "/url" && s.route.methods.post
    );
  
    expect(postUrlRoute?.route?.stack[0].name).toBe("auth");
    expect(postUrlRoute?.route?.stack[1].name).toBe('isBlack');
    expect(postUrlRoute?.route?.stack[2].name).toBe('bound create');

  });
  
  it("should call urlController.redirect method when GET /:code is called", () => {
    const getUrlRoute = urlRoutes.getRouter().stack.find(
      (s) => s.route && s.route.path === "/:code" && s.route.methods.get
    );
    expect( getUrlRoute?.route?.stack[0].name).toBe('isBlack');
    expect( getUrlRoute?.route?.stack[1].name).toBe('bound redirect');
  });
  
});
