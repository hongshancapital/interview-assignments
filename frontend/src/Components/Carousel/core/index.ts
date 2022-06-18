/* eslint-disable no-dupe-class-members */
import PluginDriver from './PluginDriver'
import { HookType, Plugin, Status, CoreResult, CarouselHookConfig, CoreHook } from './type'

// eslint-disable-next-line no-use-before-define
type FullPlugin = Required<Plugin<CarouselHookConfig, Carousel>>

export type NormalPlugin = Partial<Omit<FullPlugin, CoreHook>>

export type CorePlugin = Pick<FullPlugin, CoreHook> & NormalPlugin
export default class Carousel {
  protected status: Status | -2 = -2

  protected index: number = 0

  protected length!: number

  protected initTask: Promise<void> | null = null

  protected pluginDriver = new PluginDriver<CarouselHookConfig, Carousel>({
    init: HookType.async,
    mounted: HookType.sync,
    wait: HookType.first,
    waiting: HookType.parallel,
    jump: HookType.first,
    jumping: HookType.parallel,
    jumpEnd: HookType.sync,
    pause: HookType.sync,
    unmount: HookType.sync
  })

  constructor (corePlugin: CorePlugin) {
    this.pluginDriver.use(corePlugin)
    this.pluginDriver.setContext(this)
  }

  usePlugin (plugin: NormalPlugin) {
    this.pluginDriver.use(plugin)
  }

  protected checkStatus (status: Status): boolean {
    switch (status) {
      case -1:
        return this.status === -2
      case 0:
        if (this.initTask) {
          this.initTask.then(() => {
            this.setStatus(0)
          })
          return false
        }
        return this.status === -1
      case 1:
        return [0, 2].includes(this.status)
      case 2:
        return [1, 2, 3].includes(this.status)
      case 3:
        return [1, 2].includes(this.status)
      case 4:
        return ![null, 4].includes(this.status)
    }
  }

  get current () {
    return this.index
  }

  get next () {
    return this.index + 1 < this.length ? this.index + 1 : 0
  }

  get pre () {
    return this.index - 1 < 0 ? this.length - 1 : this.index - 1
  }

  get last () {
    return this.length - 1
  }

  protected async setStatus (status: Exclude<Status, 2>): Promise<void>;
  protected async setStatus (status: 2, index: number): Promise<void>;
  protected async setStatus (status: Status, index?: number): Promise<void> {
    if (!this.checkStatus(status)) {
      return
    }
    this.status = status
    try {
      if (status === -1) {
        this.initTask = this.pluginDriver.runHook('init') || null
        if (this.initTask) {
          await this.initTask
          this.initTask = null
        }
      } else if (status === 0) {
        this.pluginDriver.runHook('mounted')
        this.waiting()
      } else if (status === 1) {
        const { task, data } = this.pluginDriver.runHook('wait', this.index) as CoreResult
        await this.pluginDriver.runHook('waiting', data, this.index)
        await task
        this.jump(this.next)
      } else if (status === 2) {
        const { task, data } = this.pluginDriver.runHook('jump', index as number) as CoreResult
        await this.pluginDriver.runHook('jumping', data, index as number)
        await task
        this.pluginDriver.runHook('jumpEnd', index as number)
        this.index = index as number
        this.waiting()
      } else if (status === 3) {
        this.pluginDriver.runHook('pause')
      } else if (status === 4) {
        this.pluginDriver.runHook('unmount')
      }
    } catch {}
  }

  async init (length: number) {
    this.length = length
    this.setStatus(-1)
  }

  async mount () {
    this.setStatus(0)
  }

  unmount () {
    this.setStatus(4)
  }

  protected waiting () {
    this.setStatus(1)
  }

  jump (index: number) {
    this.setStatus(2, index)
  }

  pause () {
    this.setStatus(3)
  }

  resume (index?: number) {
    if (this.status !== 3) {
      return
    }
    if (typeof index === 'number') {
      this.jump(index)
    } else {
      this.jump(this.index)
    }
  }
}
