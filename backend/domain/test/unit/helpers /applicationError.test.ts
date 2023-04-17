import { ApplicationError } from '../../../src/helpers/application.err';

describe('Helpers: ApplicationError', () => {
  
  it('should set status and message properties correctly', () => {
    const status = 500;
    const message = 'Internal Server Error';
    const error = new ApplicationError(status, message);
    expect(error.status).toBe(status);
    expect(error.message).toBe(message);
  });

  it('should inherit from the Error class', () => {
    const error = new ApplicationError(500, 'Internal Server Error');
    expect(error).toBeInstanceOf(Error);
  });

});