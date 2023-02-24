const router = module.exports = require('express').Router()
const co = require('co')
const BigNumber = require('bignumber.js')
const assemble = require('../utils/responser.js')
const widthstr = require('../utils/widthstr.js')

var _loop = 0

router.post('/l2s', (req, res) => {
  co(function * () {
    const {
      lstr
    } = req.body

    if (!lstr) {
      throw new Error('参数有误')
    }

    if (++_loop > 999999999999) {  // 12位
      _loop = 0
    }

    // 41位的毫秒时间戳+5位机器码+5位进程id+12位自增计数器 => 63位
    const ll = new BigNumber(
                        widthstr(Date.now().toString(2), 41)
                        + widthstr(Number(Config.machineId).toString(2), 5)
                        + widthstr(Number(process.pid).toString(2), 5)
                        + widthstr(Number(_loop).toString(2), 12)
                      , 2)

    const sstr = ll.toString(36) // 转换为36进制
    yield db.redis.set(sstr, lstr) // 入库
    res.json(assemble(0, {
      sstr
    }))

  }).catch(e => {
    console.error(e.message)
    res.status(500).json(assemble(1, {
      e: e.message
    }))
  })
})

router.get('/s2l/:id', (req, res) => {
  co(function * () {
    const { id } = req.params
    const lstr = yield db.redis.get(id)
    res.json(assemble(0, {
      lstr: lstr ? lstr : ''
    }))

  }).catch(e => {
    console.error(e.message)
    res.status(500).json(assemble(1, {
      e: e.message
    }))
  })
})
