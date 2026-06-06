import { auth } from '../../../src/middlewares/auth.middleware';
import { ApplicationError } from '../../../src/helpers/application.err';
import config from '../../../src/config/index';
import * as jwt from '../../../src/utils/jwt';

describe('Middleware: auth', () => {
  let req: any;
  let res: any;
  let next: any;

  beforeEach(() => {
    req = {
      cookies: {
        Authorization: 'Bearer token',
      },
      header: jest.fn(),
    };
    res = {};
    next = jest.fn();
  });

  afterEach(() => {
    jest.clearAllMocks();
  });


  it("should set user on the request object if authentication is successful", async () => {

    const mockUser = {
      uid: "test-id",
      username: "test-username"
    };

    jest.spyOn(config, "get").mockReturnValue("secret-key");
    jest.spyOn(jwt, 'verifyToken').mockResolvedValueOnce(mockUser);

    await auth(req, res, next);

    expect(req.user).toEqual(mockUser);
    expect(next).toBeCalled();
  });

  it('should call next with 404 if authorization is missing', async () => {
    req.cookies = {};
    jest.spyOn(req, 'header').mockReturnValue(null);

    await auth(req, res, next);

    expect(req.header).toHaveBeenCalledWith('Authorization');
    expect(next).toHaveBeenCalledWith(new ApplicationError(404, 'Authentication token missing'));
  });

  it('should call next with 401 if authorization token is wrong', async () => {
    jest.spyOn(jwt,'verifyToken').mockResolvedValue('');

    await auth(req, res, next);

    expect(jwt.verifyToken).toHaveBeenCalledWith('Bearer token', 'secret-key');
    expect(next).toHaveBeenCalledWith(new ApplicationError(401, 'Wrong authentication token'));
  });

  it("should return an error if authentication token is missing 'Bearer' prefix", async () => {
    req = {
      header: jest.fn().mockReturnValue("test-header")
    };
    jest.spyOn(config, "get").mockReturnValue("secret-key");

    await auth(req, res, next);

    expect(next).toBeCalledWith(new ApplicationError(401, "Authentication token missing"));
  });

});
