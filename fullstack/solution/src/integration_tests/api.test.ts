import 'reflect-metadata';
import * as request from 'supertest';
import container from './inversify.4test.config';
import Interfaces from '../interfaces';
import { Data_Storage_Intf } from '../data_storage/data_storage_intf';
import App from '../server/app';
import {
  STATUS_CODE_OK,
  STATUS_CODE_CREATED,
  STATUS_CODE_BAD_REQUEST,
  STATUS_CODE_NOT_FOUND,
  STATUS_CODE_INTERNAL_SERVER_ERROR,
} from '../constants';
import {
  SERVICE_ENDPOINT,
  SHORT_URL_PREFIX,
} from '../config';

const { app } = container.get<App>(App);
describe('POST / (to create)', () => {
  const original_url_data = { original_url: 'https://www.google.com' };
  it('should return \'CREATED\'', async () => {
    await request(app)
      .post(SERVICE_ENDPOINT)
      .send(original_url_data)
      .set('Accepted', 'application/json')
      .expect('Content-Type', /json/)
      .expect(STATUS_CODE_CREATED);
  });

  it('should return \'BAD REQUEST\' if already a short url', async () => {
    const wrong_original_url_data = { original_url: `${SHORT_URL_PREFIX}ABCDEFGH` };
    const res = await request(app)
      .post(SERVICE_ENDPOINT)
      .send(wrong_original_url_data)
      .set('Accepted', 'application/json')
      .expect(STATUS_CODE_BAD_REQUEST);
    expect(res.text).toMatch(/Invalid Original URL/);
  });

  it('should return \'OK\' with the result if already created before', async () => {
    await request(app)
      .post(SERVICE_ENDPOINT)
      .send(original_url_data);
    await request(app)
      .post(SERVICE_ENDPOINT)
      .send(original_url_data)
      .set('Accepted', 'application/json')
      .expect('Content-Type', /json/)
      .expect(STATUS_CODE_OK);
  });

  it('should return \'Internal Server Error\'', async () => {
    const storage = container.get <Data_Storage_Intf>(Interfaces.Storage);
    const original_url_data_x = { original_url: 'https://xxx.xxx.xxx' };
    const spy = jest.spyOn(storage, 'save_a_mapping').mockResolvedValueOnce(false);
    await request(app)
      .post(SERVICE_ENDPOINT)
      .send(original_url_data_x)
      .expect(STATUS_CODE_INTERNAL_SERVER_ERROR);
    expect(spy).toHaveBeenCalledTimes(1);
  });
});

describe('GET /:short_url (to resolve)', () => {
  it('should return \'OK\'', async () => {
    const original_url_data = { original_url: 'https://www.google.com' };
    const response = await request(app)
      .post(SERVICE_ENDPOINT)
      .send(original_url_data)
      .set('Accepted', 'application/json');

    const { short_url } = response.body;

    await request(app)
      .get(`${SERVICE_ENDPOINT}/${encodeURIComponent(short_url)}`)
      .expect(STATUS_CODE_OK)
      .expect({ short_url: `${short_url}`, original_url: 'https://www.google.com' });
  });

  it('should return \'NOT FOUND\' if not created before', async () => {
    const valid_short_url = `${SHORT_URL_PREFIX}XXXXXXXX`;
    const res = await request(app)
      .get(`${SERVICE_ENDPOINT}/${encodeURIComponent(valid_short_url)}`)
      .expect(STATUS_CODE_NOT_FOUND);
    expect(res.text).toMatch('Not Found');
  });

  it('should return \'BAD REQUEST\' if no short_url provided', async () => {
    const invalid_short_url = 'x';
    const res = await request(app)
      .get(`${SERVICE_ENDPOINT}/${invalid_short_url}`);
    expect(res.status).toEqual(STATUS_CODE_BAD_REQUEST);
    expect(res.text).toMatch(/Invalid Short URL/);
  });
});
