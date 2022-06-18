import { HookType, PluginMap, HooksConfig, Plugin, Result } from './type'

export default class PluginDriver<C extends HooksConfig, T> {
  private pluginMap: PluginMap<C, T>
  private context!: T

  constructor (config: {[K in keyof C]: C[K]['type']}) {
    const pluginMap: PluginMap<C, T> = {} as PluginMap<C, T>
    const list = Object.keys(config) as (keyof C)[]
    for (let i = 0, l = list.length; i < l; i++) {
      pluginMap[list[i]] = {
        type: config[list[i]],
        hooks: []
      }
    }
    this.pluginMap = pluginMap
  }

  setContext (ctx: T) {
    this.context = ctx
  }

  use (plugin: Plugin<C, T>): () => void {
    const keys = Object.keys(plugin)
    const removes: (() => void)[] = []
    for (let i = 0, l = keys.length; i < l; i++) {
      const hook = plugin[keys[i]]
      const item = this.pluginMap[keys[i]]
      if (typeof hook === 'function' && item) {
        item.hooks.push(hook)
        removes.push(() => {
          const ind = item.hooks.indexOf(hook)
          ~ind && item.hooks.splice(ind, 1)
        })
      }
    }
    return () => {
      removes.forEach(fn => fn())
      removes.length = 0
    }
  }

  runHook<N extends keyof C> (name: N, ...args: C[N]['params']): Result<C[N]['type'], C[N]['result']> | void {
    const item = this.pluginMap[name]
    if (!item || item.hooks.length === 0) {
      return
    }
    switch (item.type) {
      case HookType.async:
        return this.runAsyncHook(item.hooks, ...args) as Result<C[N]['type'], C[N]['result']>
      case HookType.sync:
        return this.runSyncHook(item.hooks, ...args)
      case HookType.first:
        return this.runFirstHook(item.hooks, ...args)
      case HookType.parallel:
        return this.runParallelHook(item.hooks, ...args) as Result<C[N]['type'], C[N]['result']>
    }
  }

  private runAsyncHook<N extends keyof C, P extends PluginMap<C, T>> (
    hooks: P[N]['hooks'],
    ...args: C[N]['params']
  ): Result<HookType.async, C[N]['result']> {
    let promise = Promise.resolve()
    for (let i = 0, l = hooks.length; i < l; i++) {
      promise = promise.then(() => hooks[i].call(this.context, ...args) as void)
    }
    return promise
  }

  private runSyncHook<N extends keyof C, P extends PluginMap<C, T>> (
    hooks: P[N]['hooks'],
    ...args: C[N]['params']
  ): Result<HookType.sync, C[N]['result']> {
    for (let i = 0, l = hooks.length; i < l; i++) {
      hooks[i].call(this.context, ...args)
    }
  }

  private runFirstHook<N extends keyof C, P extends PluginMap<C, T>> (
    hooks: P[N]['hooks'],
    ...args: C[N]['params']
  ): Result<HookType.first, C[N]['result']> {
    for (let i = 0, l = hooks.length; i < l; i++) {
      const result = hooks[i].call(this.context, ...args)
      if (typeof result !== 'undefined') {
        return result
      }
    }
    return args[0]
  }

  private runParallelHook<N extends keyof C, P extends PluginMap<C, T>> (
    hooks: P[N]['hooks'],
    ...args: C[N]['params']
  ): Result<HookType.parallel, C[N]['result']> {
    return Promise.all(hooks.map(hook => hook.call(this.context, ...args) as void))
  }
}
