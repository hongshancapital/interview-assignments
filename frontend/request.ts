import axios from 'axios';
import config from '../common/config';
const { SERVER_URL } = config;

const findUrl = async (shortUrl: string): Promise<any> => {
  const url = `${SERVER_URL}/url?shortUrl=${shortUrl}`;
  const resp = await axios.get(url);
  return resp && resp.data;
};

const insertUrl = async (longUrl: string): Promise<any> => {
  const url = `${SERVER_URL}/url`;
  const body = { longUrl };
  const resp = await axios.post(url, body);
  return resp && resp.data;
};

export default {
  findUrl,
  insertUrl,
};
