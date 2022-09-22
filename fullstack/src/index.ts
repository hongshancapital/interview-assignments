import * as Koa from 'koa'
import * as Router from 'koa-router'
import * as KoaBodyparser from 'koa-bodyparser'

import { CODE, MESSAGE } from './constant'
import { saveUrlAndReturnShortId, getUrlWithShortId } from './service'

const app = new Koa()
const router = new Router()

router.get('/save', async (ctx) => {

    const { url: _url } = ctx.request.query
    console.log('[_url]', _url)
    let url = Array.isArray(_url) ? _url[0] : _url

    if (!url) return ctx.body = {
        code: CODE.ERROR.QUERY_ERROR,
        message: MESSAGE.QUERY_ERROR
    }

    url = decodeURIComponent(url)
    const id = await saveUrlAndReturnShortId(url)

    return ctx.body = {
        code: CODE.SUCCESS,
        message: MESSAGE.SUCCESS,
        data: {
            id
        }
    }
})

router.get('/get', async (ctx) => {
    const { id: _id } = ctx.request.query
    let id = Array.isArray(_id) ? _id[0] : _id

    console.log('[_id]', id)

    if (!id) return ctx.body = {
        code: CODE.ERROR.QUERY_ERROR,
        message: MESSAGE.QUERY_ERROR
    }


    const url = await getUrlWithShortId(id)

    if (!url) return ctx.body = {
        code: CODE.ERROR.URL_INVALID,
        message: MESSAGE.URL_INVALID
    }

    return ctx.body = {
        code: CODE.SUCCESS,
        message: MESSAGE.SUCCESS,
        data: {
            url
        }
    }
})

router.get('/r/:id', async (ctx) => {
    const { id } = ctx.params
    if (!id) return {
        code: CODE.ERROR.URL_INVALID,
        message: MESSAGE.URL_INVALID
    }
    const url = await getUrlWithShortId(id)
    if (!url) return {
        code: CODE.ERROR.URL_INVALID,
        message: MESSAGE.URL_INVALID
    }

    ctx.redirect(url)

})




app.use(KoaBodyparser())
app.use(router.routes());

app.listen(3000)