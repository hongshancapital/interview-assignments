const EventEmitter = require('events')
const changeCase = require('change-case')
const co = require('co')

class Bus extends EventEmitter {
  message() {
    const args = []
    Array.prototype.push.apply(args, arguments)
    const topic = args.shift()
    const eventName = `on${changeCase.pascalCase(topic)}`

    for (const module of spaces.modules.values()) {
      if (typeof (module[eventName]) === 'function' && (module[eventName]).constructor.name === 'Function') {
        module[eventName].apply(module, args)
      } else if (typeof (module[eventName]) === 'function' && (module[eventName]).constructor.name === 'GeneratorFunction') {
        co(function *() {
          yield module[eventName].apply(module, args)
        }).catch(e => {
          console.error(e)
        })
      }
    }

    this.emit.apply(this, arguments)
  }
}

module.exports = new Bus
