import { expect } from "chai"
import { ShortUrl } from "./ShortUrl"

describe('generate and get url', () => {

  let shortUrl: ShortUrl
  before(async () => {
    shortUrl = new ShortUrl(require('../../config.test.js'))
  })

  let generatedId: string
  step('generate url', async () => {
    generatedId = await shortUrl.create('http://example.com')
    expect(generatedId.length).equal(8)
  })

  step('get url', async () => {
    expect(await shortUrl.get(generatedId)).to.be.equal('http://example.com')
    expect(shortUrl.get("notexist")).to.be.eventually.rejectedWith(/id_not_found/)
  })

})