import epxress from 'express';
import validUrl from 'valid-url';
import redis from './redis';
import { Shorten } from './shorten';

const router = epxress.Router();
const baseUrl = "http://127.0.0.1:3005"

router.get('/:code', async (req, res) => {
	try {
		const code = req.params.code;
		const url = await Shorten.expand(code, redis);
		if (url) {
			res.redirect(url);
		} else {
			res.status(404).json("Not Found Url");
		}
	} catch (error) {
		res.status(500).json("Server Error");
	}
});

router.post('/shorten', async (req, res) => {
	try {
		const url = req.body.url;
		if (validUrl.isUri(url)) {
			const code = await Shorten.shorten(url, redis);
			res.json({
				shortUrl: `${baseUrl}/${code}`,
				code
			});
		} else {
			console.log('Invalid url');
			res.status(400).json('Invalid url');
		}
	} catch (error) {
		res.status(500).json("Server Error");
	}
});

export default router;