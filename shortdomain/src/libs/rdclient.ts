import Redis from 'ioredis'
import conf from '../config/redis'

export default new Redis(conf)