/* eslint-disable no-dupe-class-members */
import { HookConf, Hook, Hooks, HookMap, CoreHook, Status } from './type'

export default class Carousel {
  protected status: Status = Status.beforeInit

  protected index: number = 0

  protected length!: number

  protected initTask: Promise<void> | null = null

  protected waitHook: CoreHook<Carousel>['wait']

  protected jumpHook: CoreHook<Carousel>['jump']

  protected hooks: HookMap<Carousel> = {
    init: [],
    mounted: [],
    waiting: [],
    jumping: [],
    jumpEnd: [],
    pause: [],
    unmount: []
  }

  constructor (coreHooks: CoreHook<Carousel> & Hooks<Carousel>) {
    const { wait, jump, ...rest } = coreHooks
    this.waitHook = wait.bind(this)
    this.jumpHook = jump.bind(this)
    this.addHooks(rest)
  }

  addHooks (hooks: Hooks<Carousel>) {
    Object.keys(hooks).forEach((k) => {
      const list = this.hooks[<keyof HookConf>k]
      list && list.push(hooks[<keyof (typeof hooks)>k] as Hook<Carousel>)
    })
  }

  protected checkStatus (status: Status): boolean {
    switch (status) {
      case Status.init:
        return this.status === Status.beforeInit
      case Status.mounted:
        if (this.initTask) {
          this.initTask.then(() => {
            this.setStatus(0)
          })
          return false
        }
        return this.status === Status.init
      case Status.waiting:
        return [Status.mounted, Status.jump].includes(this.status)
      case Status.jump:
        return [Status.waiting, Status.jump, Status.pause].includes(this.status)
      case Status.pause:
        return [Status.waiting, Status.jump].includes(this.status)
      case Status.unmount:
        return ![Status.beforeInit, Status.unmount].includes(this.status)
      default:
        return false
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

  protected async setStatus (status: Exclude<Status, Status.jump>): Promise<void>;
  protected async setStatus (status: Status.jump, index: number): Promise<void>;
  protected async setStatus (status: Status, index?: number): Promise<void> {
    if (!this.checkStatus(status)) {
      return
    }
    this.status = status
    try {
      if (status === Status.init) {
        this.initTask = this.hooks.init.length
          ? this.hooks.init.reduce(
            (res, fn) => res.then(() => fn.call(this)),
            Promise.resolve()
          )
          : null
        if (this.initTask) {
          await this.initTask
          this.initTask = null
        }
      } else if (status === Status.mounted) {
        this.hooks.mounted.forEach(fn => fn.call(this))
        this.waiting()
      } else if (status === Status.waiting) {
        const { task, data } = this.waitHook()
        await Promise.all(this.hooks.waiting.map(fn => fn.call(this, data, this.current)))
        await task
        this.jump(this.next)
      } else if (status === Status.jump) {
        const { task, data } = this.jumpHook(index as number)
        await Promise.all(this.hooks.jumping.map(fn => fn.call(this, data, index as number)))
        await task
        this.hooks.jumpEnd.forEach(fn => fn.call(this, index as number))
        this.index = index as number
        this.waiting()
      } else if (status === Status.pause) {
        this.hooks.pause.forEach(fn => fn.call(this))
      } else if (status === Status.unmount) {
        this.hooks.unmount.forEach(fn => fn.call(this))
      }
    } catch {}
  }

  async init (length: number) {
    this.length = length
    this.setStatus(Status.init)
  }

  async mount () {
    this.setStatus(Status.mounted)
  }

  unmount () {
    this.setStatus(Status.unmount)
  }

  protected waiting () {
    this.setStatus(Status.waiting)
  }

  jump (index: number) {
    this.setStatus(Status.jump, index)
  }

  pause () {
    this.setStatus(Status.pause)
  }

  resume (index?: number) {
    if (this.status !== Status.pause) {
      return
    }
    if (typeof index === 'number') {
      this.jump(index)
    } else {
      this.jump(this.index)
    }
  }
}

export type CarouselCoreHooks = CoreHook<Carousel>

export type CarouselHooks = Hooks<Carousel>

export type { CoreResult } from './type'
