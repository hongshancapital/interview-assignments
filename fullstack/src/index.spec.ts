import chai, { assert, expect } from "chai"
import 'chai-http'
import { createAppServer } from './index'

describe('generate and get url', () => {

  let id: string
  step('generate url', async () => {
    const { app } = await createAppServer()
    const req = await chai.request(app).post('/').send({ url: 'http://exmaple.com' })
    assert('id' in req.body)
    assert('url' in req.body)

    id = req.body.id
  })

  step('generate url', async () => {
    const { app } = await createAppServer()
    const req = await chai.request(app).post('/').send({ url: 'whatever' })
    expect(req.status).eq(400)
    assert('code' in req.body)
    expect(req.body.code).eq('url_check_failed')
  })

  step('generate url', async () => {
    const { app } = await createAppServer()
    const req = await chai.request(app).get(`/${id}`).redirects(0)
    expect(req.status).eq(302)
    expect(req).to.redirectTo('http://exmaple.com')
  })

})