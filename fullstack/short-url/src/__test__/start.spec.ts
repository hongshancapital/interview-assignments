import axios, { AxiosInstance } from 'axios';
import { stopApp } from '@/app';

describe('start', () => {
  let axiosAPIClient: AxiosInstance;

  beforeAll(async () => {
    const axiosConfig = {
      baseURL: `http://127.0.0.1:${process.env.PORT || 3000}`,
      validateStatus: () => true, //Don't throw HTTP exceptions. Delegate to the tests to decide which error is acceptable
    };
    axiosAPIClient = axios.create(axiosConfig);
  });

  afterEach(async () => {
    await stopApp();
  });

  test('should pass health check when start', async () => {
    await import('../start');
    const response = await axiosAPIClient.get('/');

    expect(response).toMatchObject({
      status: 200,
      data: {
        message: 'Ok',
      },
    });
  });
});
