import { Url } from './url.entity';

export class Table<T> {
  nextId = 1;
  records: { [key: number]: T } = {};
}

export class DbData {
  urls: Table<Url> = new Table<Url>();
}
