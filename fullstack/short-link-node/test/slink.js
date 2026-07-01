const request = require('request')
const assert = require('assert')
const HOST = 'http://localhost:3777'
const slink_noexist = 'xxxxxxxxx'
const slink_query = 'p98'
const appid = 'unit-test-appid'
const oriUrl_baidu = 'https://baidu.com'
const oriUrl_163 = 'https://www.163.com/sports/article/H7BARQL800058780.html'
const apiReq = options => {
  return new Promise((resolve, reject) => {
    request(options, function (error, response, body) {
      let ret = {}
      try {
        ret = JSON.parse(body)
      } catch (error) {
        ret = body
      }
      resolve(ret)
    })
  })
}
const requestUrl = (method, url, data, cb) => {
  return async () => {
    let ret = {}
    let options = {
      url: HOST + url,
      method: method,
      headers: {
        'content-type': 'application/json'
      }
    }
    if (method === 'GET') {
      options.qs = data
    } else {
      options.body = JSON.stringify(data)
    }
    try {
      ret = await apiReq(options)
    } catch (error) {}
    cb(ret)
  }
}

let slink = ''
let step = 0

describe('#Test Short Link', function () {
  describe('#Add API /man/a', function (done) {
    it(
      'appid is empty',
      requestUrl('POST', '/man/a', { oriUrl: oriUrl_163 }, data => {
        assert.deepEqual(
          data,
          { code: '201010', msg: 'appid没有经过验证' },
          '预期返回缺少比必填字端'
        )
      })
    )
    it(
      'oriUrl is empty',
      requestUrl('POST', '/man/a', { appid }, data => {
        assert(data.code != '00000' && data.msg)
      })
    )
    it(
      'Add Return slink',
      requestUrl('POST', '/man/a', { appid, oriUrl: oriUrl_163 }, data => {
        slink = data.data.slink
        step = 1
        assert(data.code == '000000' && data.data.slink, '预期返回slink')
      })
    )
  })

  describe('#Query API /man/q', function () {
    it(
      'slink is empty',
      requestUrl('GET', '/man/q', { appid }, data => {
        return assert.deepEqual(
          data,
          { code: '201210', msg: 'slink没有经过验证' },
          '预期返回缺少比必填字端'
        )
      })
    )
    it(
      'slink non-existent',
      requestUrl('GET', '/man/q', { appid, slink: 'xxxddsdsdsds' }, data => {
        return assert.deepEqual(
          data,
          { code: '101299', msg: '没有找到' },
          '预期返回slink不存在'
        )
      })
    )
    it(
      'slink existent',
      requestUrl('GET', '/man/q', { appid, slink: slink_query }, data => {
        // console.log(data, slink, "uuuuuu")
        assert.equal(data.data.oriUrl, oriUrl_baidu, '预期返回oriUrl')
      })
    )
  })

  describe('#Update API /man/u', function () {
    it(
      'slink is empty',
      requestUrl('POST', '/man/u', { appid, oriUrl: oriUrl_163 }, data => {
        return assert.deepEqual(
          data,
          { code: '201110', msg: 'slink没有经过验证' },
          '预期返回缺少比必填字端'
        )
      })
    )
    it(
      'oriUrl is empty',
      requestUrl('POST', '/man/u', { appid, slink: slink_query }, data => {
        return assert.deepEqual(
          data,
          { code: '201110', msg: 'oriUrl没有经过验证' },
          '预期返回缺少比必填字端'
        )
      })
    )
    it(
      'update oriUrl ',
      requestUrl(
        'POST',
        '/man/u',
        { appid, slink: slink_query, oriUrl: oriUrl_163 },
        data => {
          return assert.equal(data.data.oriUrl, oriUrl_163, '预期返回修改成功')
        }
      )
    )

    it(
      'slink diff ',
      requestUrl('GET', '/man/q', { appid, slink: slink_query }, data => {
        // console.log(data, slink, "uuuuuu")
        assert.equal(data.data.oriUrl, oriUrl_163, '预期返回值为修改后的值')
      })
    )
    it(
      'reverter oriUrl ',
      requestUrl(
        'POST',
        '/man/u',
        { appid, slink: slink_query, oriUrl: oriUrl_baidu },
        data => {
          return assert.equal(
            data.data.oriUrl,
            oriUrl_baidu,
            '预期返回原是地址为baidu'
          )
        }
      )
    )
  })

  describe('#Page direct API /l/:slink', function () {
    it(
      'Page direct to baidu',
      requestUrl('GET', `/l/${slink_query}`, {}, data => {
        const index = data.indexOf('百度一下，你就知道')
        return assert(index > 0, '打开百度')
      })
    )
  })
})
