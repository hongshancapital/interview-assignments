import compose from 'koa-compose'
import Router from 'koa-router'

// import test from './test'
import shorturl from './api/shorturl'

const children = [
  { routes: shorturl, prefix: '/api' },
]

export default function routes () {
  const router = new Router()

  router
    /** Print the routes list */
    .get('/api', (ctx) => {
      const map = new Map<string, Set<string>>()
      router.stack.forEach(layer => {
        if (map.has(layer.path)) {
          const methods = map.get(layer.path) as Set<string>
          layer.methods.forEach(method => methods.add(method))
        } else {
          map.set(layer.path, new Set(layer.methods))
        }
      })
      ctx.body = [...map.entries()].map(([path, methods]) => `[${[...methods].join(' ')}] ${path}`)
    })

  // Nested routers
  children.forEach(child => {
    const nestedRouter = new Router()
    child.routes(nestedRouter)
    router.use(child.prefix, nestedRouter.routes(), nestedRouter.allowedMethods())
  })

  return compose([router.routes(), router.allowedMethods()])
}
