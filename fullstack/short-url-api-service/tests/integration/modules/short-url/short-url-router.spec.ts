
process.env.NODE_ENV = 'dev';
import app from '../../../../src/app'
import chaiHttp from 'chai-http'
import chai from "chai"
chai.use(chaiHttp)
const { request, expect } = chai

describe('modules/short-url/short-url-router', function () {

    this.timeout(5000);


    before((done) => {
        app.on('listened', function () {
            done()
        });
    })

    const routerBasePath = '/short-url'
    const shortUrlBase = process.env.SHORT_URL_BASE
    const shortUrl = shortUrlBase + 'xxx'
    describe('http get short-url/url/{short url}', () => {
        it('it should return 200 with data is null when shortUrl is ' + shortUrl, (done) => {
            request(app)
                .get(routerBasePath + '/url/' + encodeURIComponent(shortUrl))
                .end((err, res) => {
                    expect(res.status).to.equal(200)
                    expect(res.body.data).to.be.null
                    done();
                });
        });

    })

    let longUrl = 'https://circle-stand.com/index.html?a=1#part3'
    let createdShortUrl: string | undefined = undefined
    describe('http post short-url/', () => {
        it('it should return 400 when body not contain long_url', (done) => {
            request(app)
                .post(routerBasePath + '/')
                .end((err, res) => {
                    expect(res.status).to.equal(400)
                    done();
                });
        });
        it(`it should return 200 with data is when long_url in body is ${longUrl}`, async () => {
            try {
                const res = await request(app)
                    .post(routerBasePath + '/')
                    .send({
                        long_url: longUrl,
                    })
                expect(res.status).to.equal(200)
                createdShortUrl = res.body.data
            }
            catch (err) {
                console.error(err)
            }
        });
    })

    describe(`http get short-url/url/ with last created short url}`, () => {
        it(`it should return 200 with data is ${longUrl} `, (done) => {
            request(app)
                .get(routerBasePath + '/url/' + encodeURIComponent(createdShortUrl!))
                .end((err, res) => {
                    expect(res.status).to.equal(200)
                    expect(res.body.data).to.equal(longUrl)
                    done();
                });
        });

    })


})
