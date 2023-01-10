import 'reflect-metadata';
import faker from 'faker';
import { BASE_URL } from '@base/env';

import ShortenUrlService from '@modules/urls/services/ShortenUrlService';
import FakeUrlRepository from '../fakes/repositories/FakeUrlRepository';

describe('Shorten Url Service', () => {

  let shortenUrlService: ShortenUrlService;
  let fakeUrlRepository: FakeUrlRepository;

  beforeAll(async () => {
    fakeUrlRepository = new FakeUrlRepository();
    shortenUrlService = new ShortenUrlService(fakeUrlRepository);
  });

  it('should create shorten url', async () => {
    const url = faker.internet.url();
    const newUrl = await shortenUrlService.create(url);

    expect(newUrl).toHaveLength(6);
    expect(await fakeUrlRepository.findByShortPath(newUrl)).toBe(url);
  });

  it('should return original url', async () => {
    const url = faker.internet.url();
    const shortPath = await fakeUrlRepository.createShortUrl(url);
    const response = await shortenUrlService.find(BASE_URL + shortPath);

    expect(response).toBe(url);
  });
});
