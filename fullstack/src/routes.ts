import { ShortUrlController } from "./controller/ShortUrlController"

export const Routes = [
	{
		method: "post",
		route: "/api/shortUrl",
		controller: ShortUrlController,
		action: "longUrlToShortUrl",
	},
	{
		method: "get",
		route: "/api/longUrl",
		controller: ShortUrlController,
		action: "getLongUrl",
	},
]
