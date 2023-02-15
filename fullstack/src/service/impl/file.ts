import { ShortLinkServiceBase } from '../base';

export class File extends ShortLinkServiceBase {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars, no-unused-vars
  saveLink(link: string): string | Promise<string> {
    throw new Error('Method not implemented.');
  }
  // eslint-disable-next-line @typescript-eslint/no-unused-vars, no-unused-vars
  getLink(id: string): string | Promise<string> {
    throw new Error('Method not implemented.');
  }
}
