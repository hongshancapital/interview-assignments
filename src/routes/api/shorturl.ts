import Router from 'koa-router'

// import urlControllers from '../../controllers/url'
import controllers from '../../controllers'

export default (router: Router) => {
  router
    .post('/urls', controllers.url.saveUrlCont )
    .get('/urls', controllers.url.getLongUrlByShortCont)
}
