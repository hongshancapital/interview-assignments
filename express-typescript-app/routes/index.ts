/**
 * index.js
 * @authors lizilong
 * @date    2022-05-10
 * @description 总的路由控制文件
 */

import urlLongToShort from '../api/urlLongToShort'
import urlShortToLong from '../api/urlShortToLong'
module.exports = (app:any) => {
	app.use('/', urlShortToLong)
	app.use('/api/urlLongToShort', urlLongToShort)
}
