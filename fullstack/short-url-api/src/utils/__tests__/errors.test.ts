import { CustomError, HttpStatusCodes } from '../errors';

const mockErrors = [
  { status: HttpStatusCodes.BAD_REQUEST, message: 'Bad request error' },
  { status: HttpStatusCodes.NOT_FOUND, message: 'Not found error' },
  { status: HttpStatusCodes.UNAUTHORIZED, message: 'Unauthorized error' },
];

describe('Custom errors tests', () => {
  it('bad request error', () => {
    mockErrors.forEach(({ status, message }) => {
      const error = new CustomError(message, status);

      expect(error.message).toEqual(message);
      expect(error.httpStatus).toEqual(status);
    });
  });
});
