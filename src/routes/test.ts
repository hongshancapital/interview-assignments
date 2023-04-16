import Router from 'koa-router'
import jwt from 'jsonwebtoken'
import config from 'config'

export default (router: Router) => {
  router
    .post('/test/token', async ctx => {
      const key = process.env.jwtSecret ?? config.get<string>('jwtSecret')
      ctx.body = { token: jwt.sign({ username: ctx.request.body.username as string }, key) }
    })
    .get('/test/auth', ctx => { ctx.body = `Welcome ${ctx.state.user.username as string}!` })
    .get('/test/error', async () => { throw Error('Error handling works!') })
    .get('/test/400', ctx => {
      ctx.status = 400
      ctx.body = { status: { code: 1003, message: 'something went wrong' } }
    })
    .get('/test/301', ctx => { ctx.status = 301 })
}
