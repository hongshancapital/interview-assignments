const { version, description } = require('../package.json')
import path from 'path'

const { SwaggerRouter } = require('koa-swagger-decorator')

const swaggerRouterOpts = {
  title: '短网址转换',
  description,
  version,
  swaggerHtmlEndpoint: '/swagger',
}
const router = new SwaggerRouter({}, swaggerRouterOpts)
router.swagger()
router.mapDir(path.resolve(__dirname, './controller'), {
  doValidation: false,
})

// dump swagger json
router.dumpSwaggerJson({
  filename: 'swagger.json', // default is swagger.json
  dir: process.cwd(), // default is process.cwd()
})

router.get('/', (ctx: any) => {
  ctx.body = 'home'
})

export default router
