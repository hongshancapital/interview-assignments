import { Client, Entity, Schema, Repository } from 'redis-om';

const client = new Client()

export interface LinkParams {
  tag: string; // 短链结尾部分，方便索引
  short_link?: string; // 短链接
  page_url?: string; // 长链
}

class Link extends Entity {}
class Index extends Entity {}

const linkSchema = new Schema(Link, {
  short_link: { type: 'string' },
  page_url: { type: 'string' },
});

const indexSchema = new Schema(Index, {
  id: { type: 'number' },
});

let linkRepository: Repository<Link>;
let indexRepository: Repository<Index>;const map = {};

export async function init(): Promise<any> {
  await client.open('redis://10.10.10.18:6379');
  linkRepository = client.fetchRepository(linkSchema);
  indexRepository = client.fetchRepository(indexSchema);
  await resetIndex();
  const index: any = indexRepository.createEntity();
  index.id = 1;
  await indexRepository.save(index);
}

export async function putLink(params: LinkParams) {
  const link = linkRepository.createEntity();
  Object.keys(params).forEach((key: string) => link[`${key}`] = params[key]);
  const id = await linkRepository.save(link);
}

export async function getLink(tag: string) {
  const item: any =
    linkRepository.search().where('tag').equals(tag).return.first();
  return item?.page_url;
}

export async function getThenIncrementIndex(): Promise<number> {
  const index = await indexRepository.search().return.first();
  const {id } = index || {} as any;
  Object.assign(index, { id: id + 1 });
  await indexRepository.save(index);
  return id;
}

export async function resetIndex(): Promise<void> {
  const index = await indexRepository.search().return.first();
  const { id } = index || {} as any;
  Object.assign(index, { id: 1 });
  await indexRepository.save(index);
  return id;
}