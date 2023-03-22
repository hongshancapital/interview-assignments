/*
  简单的一个日志类
*/

const fmt = require('util').format
const path = require('path')
const fs = require('fs')
const moment = require('moment')

class Log {
  constructor(level, noDate) {
    this.streaml = fs.createWriteStream(path.resolve(ROOTPATH, `${Config.port}-out.xlog`), { flags: 'a' })
    this.streamr = fs.createWriteStream(path.resolve(ROOTPATH, `${Config.port}-err.xlog`), { flags: 'a' })
    this.DEBUG = 4
    this.LOG = 3
    this.WARNING = 2
    this.ERROR = 1
    this.FATAL = 0
    if (typeof level === 'string') {
      level = this[level.toUpperCase()]
    }
    this.level = level || this.DEBUG
    this.noDate = noDate
  }
  debug() {
    this._write('DEBUG', arguments)
  }
  log() {
    this._write('LOG', arguments)
  }
  warn() {
    this._write('WARNING', arguments)
  }
  error() {
    this._write('ERROR', arguments)
  }
  fatal() {
    this._write('FATAL', arguments)
  }
  _write(level, args) {
    if (this.noDate) {
      var msg = `${fmt.apply(null, args)}`
    } else {
      msg = `${fmt.apply(null, args)} [${moment().format('YYMMDD:HHmm')}]`
    }

    if (this[level] <= this.level) {
      var stream
      if (this[level] >= this.WARNING) {
        stream = this.streaml
      } else {
        stream = this.streamr
      }
      stream.write(`${msg}\n`)
    }
  }
}

module.exports = Log
