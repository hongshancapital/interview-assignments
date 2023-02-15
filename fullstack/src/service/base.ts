import { isEmpty } from '../utils';

export interface IShortLinkService {
  parseShortLink: (link: string) => {
    error?: string;
    id?: string;
  };
  saveLink: (link: string) => Promise<string> | string;
  getLink: (id: string) => Promise<string> | string;
}

export type ShortLinkParams = {
  host: string;
  [key: string]: string | string[];
};

export abstract class ShortLinkServiceBase implements IShortLinkService {
  params: ShortLinkParams;
  constructor(params: ShortLinkParams) {
    this.params = params;
  }
  formatLink(id: string) {
    return `https://${this.params.host}/${id}`;
  }
  parseShortLink(link: string): {
    error?: string | undefined;
    id?: string | undefined;
  } {
    if (isEmpty(link)) {
      return { error: 'link is null' };
    }
    // valid link format: http://sl.cn/:id
    const res = link.match(/^https?:\/\/([^/]+)\/(.{8})$/);

    if (!res) {
      return { error: 'link format error' };
    }
    const [, host, id] = res;

    if (host !== this.params.host) {
      return { error: 'host is invalid' };
    }

    return { id };
  }
  abstract saveLink(link: string): string | Promise<string>;
  abstract getLink(id: string): string | Promise<string>;
}
