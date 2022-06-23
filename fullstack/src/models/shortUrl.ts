import { Shorter } from '../../src/server/lib'
import { short_urls } from '@prisma/client'
export interface ShortUrl extends short_urls {
	id: number;
	long_url: string;
	short_path?: string;
	short_url?: string;
}

export class ShortUrlModel implements ShortUrl {
	id: number;
	long_url: string;
	created_at: Date;
	constructor() {
		this.id = 0;
		this.long_url = '';
		this.created_at = new Date();
	}
	get short_url(): string {
		let host: string = process.env.HOST || 'http://localhost';
		let port: string = process.env.PORT || '3000';
		if (host.lastIndexOf('/') === host.length) {
			host += host.substring(0, host.length - 1)
		}
		if (port !== '80') {
			host += `:${port}`
		}
		return host + '/s/' + this.short_path;
	}
	get short_path(): string {
		return this.id > 0 ? Shorter.idToStr(this.id) : '';
	}
	public toJSON(): ShortUrl {
		return {
			id: this.id,
			long_url: this.long_url,
			short_path: this.short_path,
			short_url: this.short_url,
			created_at: this.created_at,
		};
	}
}