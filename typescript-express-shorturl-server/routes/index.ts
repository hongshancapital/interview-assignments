/**
 * index.ts
 * @authors lizilong
 * @description 总的路由控制文件
 */

import urlLongToShort from '../api/urlLongToShort'
import urlShortToLong from '../api/urlShortToLong'
export default (app: any) => {
	app.use('/', urlShortToLong)
	app.use('/api/urlLongToShort', urlLongToShort)
}
