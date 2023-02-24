const assert = require('assert')

function assemble(code, r) {
  assert(code === 0 || code === 1, 'code-err')
  if (code === 0) {
    if (r) {
    } else {
      r = {}
    }
    return { code, r, errMsg: '', timestamp: new Date() }
  } else {
    const errMsg = r.e ? r.e : ''
    return { code, r: {}, errMsg, timestamp: new Date() }
  }
}

module.exports = assemble
