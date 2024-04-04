import { rateLimit } from 'express-rate-limit'

export const createRateLimiter = (windowSec: number, maxRate: number) => (rateLimit({
	windowMs: windowSec * 1000, 
	max: maxRate, // Limit each IP to 100 requests per `window`
	standardHeaders: 'draft-7', // Set `RateLimit` and `RateLimit-Policy`` headers
	legacyHeaders: false, // Disable the `X-RateLimit-*` headers
	// store: ... , // Use an external store for more precise rate limiting
}))