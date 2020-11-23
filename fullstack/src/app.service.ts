import { Injectable, Inject } from '@nestjs/common';
import { InjectRepository } from '@nestjs/typeorm';
import { getConnection } from 'typeorm';
import { Shorten } from './entity/shorten';
import { TrackLink } from './entity/tracklink';
import { nanoid } from 'nanoid';

@Injectable()
export class AppService {
	constructor() {}

	async shortenerLink(body): Promise<Shorten> {
		let str_link = body.link;
		const entityManager = getConnection().mongoManager;

		let existObj = await entityManager.findOne(Shorten, {
			link: str_link,
		});

		if (existObj != undefined) {
			return existObj;
		} else {
			let shortenObj = new Shorten();
			shortenObj.link = str_link;
			let new_shorten = await entityManager.save(shortenObj);
			new_shorten.shortened = nanoid(8);
			return await entityManager.save(new_shorten);
		}
	}

	async retrieveShortened(id): Promise<string> {
		try {
			const entityManager = getConnection().mongoManager;
			let shortenObj = await entityManager.findOne(Shorten, {
				shortened: id,
			});
			return shortenObj.link;
		} catch (error) {
			return 'Link no found';
		}
	}

	async trackShortened(id, req) {
		const entityManager = getConnection().mongoManager;
		let shortenObj = await entityManager.findOne(Shorten, {
			shortened: id,
		});
		// console.log(req);
		// console.log(req.connection.remoteAddress);
		// console.log(req.ip);
		// console.log(req.headers['x-forwarded-for']);
		if (shortenObj != undefined) {
			let trackLink = new TrackLink();
			trackLink.shortened = shortenObj.shortened;
			trackLink.remoteAddress =
				req.headers['x-forwarded-for'] || req.connection.remoteAddress;
			trackLink.userAgent = req.headers['user-agent'];
			await entityManager.save(trackLink);
		}
	}
}
