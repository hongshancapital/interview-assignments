interface DomainModel {
    _id: string
    /** 映射网址的名称 */
    name?: string
    /** 长域名地址 */
    url: string
    /** 短域名信息 */
    compressed: string
    /** 映射方式，自定义根域名还是原始根域名 */
    type: 'default' | 'custom'
    createdAt: Date
    updatedAt: Date
}

type progressConfigParams = 'NODE_ENV' | 'SERVER_NAME' | 'TRACE_LOG_LEVEL' | 'DEV_LOG_LEVEL' | 'AUDIT_LOG_LEVEL' | 'JWT_APP_NAME' | 'JWT_APP_ID' | 'JWT_APP_SECERT' | 'REDIS_URL' |
    'MONGO_URL'
