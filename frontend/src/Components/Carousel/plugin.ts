import { noop } from '../../utils/share'
import type { CarouselCoreHooks as CoreHook, CarouselHooks as Hooks } from './core'
import {
  PluginFactoryResult as R,
  CorePluginOpt,
  CorePluginResult,
  DotPluginOpt,
  DotPluginResult
} from './type'

export function corePluginFactory (opt: CorePluginOpt): R<CorePluginResult, CoreHook & Hooks> {
  let stepTime = opt.stepTime
  let interval = opt.interval
  let transitionEnd = noop
  let breakJump = noop
  let breakWait = noop
  const breakAll = () => {
    breakJump()
    breakWait()
  }
  return {
    plugin: {
      jump (index) {
        breakAll()
        return {
          task: new Promise((resolve, reject) => {
            transitionEnd = resolve
            breakJump = reject
            opt.setTranslate(index)
            index === this.current && resolve()
          }),
          data: stepTime
        }
      },
      wait () {
        return {
          task: new Promise((resolve, reject) => {
            setTimeout(resolve, interval)
            breakWait = reject
          }),
          data: interval
        }
      },
      pause: breakAll,
      unmount () {
        breakAll()
      }
    },
    data: {
      setInterval (time) {
        interval = time
      },
      setStepTime (time) {
        stepTime = time
      },
      transitionEnd () {
        transitionEnd()
      }
    }
  }
}

export function dotPluginFactory (opt: DotPluginOpt): R<DotPluginResult> {
  let { onChange, frameTime } = opt
  let useDot = opt.enableDot
  let current = -1
  let pause = false
  const changePause = (value: boolean) => {
    pause = value
  }
  const enablePause = changePause.bind(null, true)
  // const runLoop = (fn: (t: number) => boolean) => {
  //   requestAnimationFrame(t => {
  //     const canContinue = fn(t)
  //     canContinue && runLoop(fn)
  //   })
  // }
  const runLoop = (fn: (t: number) => boolean) => {
    setTimeout(() => {
      fn(Date.now()) && runLoop(fn)
    }, frameTime)
  }
  return {
    plugin: {
      waiting (time, index) {
        if (!useDot) {
          return
        }
        current = index
        let start: null | number = null
        runLoop(t => {
          start === null && (start = t)
          if (pause) {
            return false
          }
          const value = t - start
          if (value >= time) {
            onChange(100)
            return false
          }
          onChange(value / time * 100)
          return true
        })
      },
      jumping () {
        onChange(100)
        enablePause()
      },
      jumpEnd: changePause.bind(null, false),
      pause: enablePause
    },
    data: {
      getCurrent () {
        return current
      },
      setEnableDot (enable) {
        useDot = enable
      },
      clean () {
        onChange = noop
      }
    }
  }
}
