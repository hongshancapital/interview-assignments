import { caching } from "cache-manager";

export default caching('memory', {
    max: 100000,
    ttl: 20 * 1000 /*milliseconds*/,
});