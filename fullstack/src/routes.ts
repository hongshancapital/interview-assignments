import { ShortUrlController } from "./controller/ShortUrlController"

export const Routes = [{
    method: "post",
    route: "/shortUrl",
    controller: ShortUrlController,
    action: "getShortUrl"
}, {
    method: "get",
    route: "/longUrl",
    controller: ShortUrlController,
    action: "getLongUrl"
}]