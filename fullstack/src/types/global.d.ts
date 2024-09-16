declare global {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  type Func = (...arg: any[]) => any
  namespace NodeJS {
    interface ProcessEnv {
      NODE_ENV: 'development' | 'production' | 'test'
      MONGODB_URL: string
      MONGODB_NAME: string
      REDIS_URL: string
      PORT?: string
      CACHE_EXPIRE_TIME: string
      STEP_SIZE: string
      BASE_DOMAIN: string
      MAX_PATH_LENGTH: string
    }
  }
}

export {}
