import { useEffect, useState, useRef, useMemo } from 'react'
import { getNext, runLoopFactory } from './utils'
import { noop, useTemporary } from '../../utils/share'
import { CarouselStatus as Status, CoreProps, CoreResult, DotProps, DotResult, DraggerProps, DraggerResult } from './type'

export const useCarousel = (props: CoreProps): CoreResult => {
  const [status, setStatus] = useState(Status.waiting)
  const [target, setTarget] = useState(0)

  const [temporary, updateTemporary] = useTemporary({
    reject: noop,
    props,
    status,
    jumpTarget: 0
  })

  updateTemporary({ props, status })

  const data = useRef({
    current: 0,
    transitionEnd: noop,
    jump (index: number) {
      temporary.reject()
      temporary.jumpTarget = index
      setStatus(Status.jumping)
    },
    pause () {
      setStatus(Status.pause)
    },
    resume (index: number) {
      if (temporary.status !== Status.pause) {
        return
      }
      data.current.jump(index)
    }
  })

  useEffect(() => {
    const tem = temporary
    if (status === Status.waiting) {
      new Promise((resolve, reject) => {
        tem.reject = reject
        setTimeout(resolve, tem.props.interval)
      }).then(() => {
        tem.jumpTarget = getNext(data.current.current, tem.props.length)
        setStatus(Status.jumping)
      }, noop)
    } else if (status === Status.jumping) {
      new Promise<void>((resolve, reject) => {
        tem.reject = reject
        data.current.transitionEnd = resolve
        data.current.current === tem.jumpTarget ? resolve() : setTarget(tem.jumpTarget)
      }).then(() => {
        data.current.current = tem.jumpTarget
        setStatus(Status.waiting)
      }, noop)
    } else if (status === Status.pause) {
      tem.reject()
    }
  }, [status, temporary, data])

  return {
    status,
    target,
    ...data.current
  }
}

const FRAME_TIME = 50
export const useDot = (...args: DotProps): DotResult => {
  const [config, data] = args
  const [progress, setProgress] = useState(0)
  // 避免每次执行 runLoopFactory
  const loopData = useMemo(runLoopFactory, [])
  const [temporary, updateTemporary] = useTemporary({
    time: 0,
    ...loopData,
    current: -1
  })
  config.enableDot && updateTemporary({ time: config.time })

  useEffect(() => {
    if (!config.enableDot) {
      return
    }
    const tmp = temporary
    if (data.status === Status.waiting) {
      tmp.runLoop((() => {
        let start: null | number = null
        return t => {
          start === null && (start = t)
          const value = t - start
          if (value >= tmp.time) {
            setProgress(100)
            return false
          }
          setProgress(value / tmp.time * 100)
          return true
        }
      })(), FRAME_TIME)
    } else if (data.status === Status.jumping) {
      setProgress(100)
    } else if (data.status === Status.pause) {
      tmp.cleanLoop()
    }
  }, [data.status, config.enableDot, temporary])

  // 由于 setProgress 是在 effect 中，因此 current 修改也需要在 effect 中，否则可能出现一帧 waiting start 时 progress 为 100
  useEffect(() => {
    temporary.current = data.current
  }, [data, temporary])

  let isCurrent = true
  if (config.enableDot && temporary.current !== data.current) {
    temporary.cleanLoop()
    isCurrent = false
  }

  return {
    getProgress (index) {
      return isCurrent && index === data.current ? progress : 0
    }
  }
}

export const useDragger = (...args: DraggerProps): DraggerResult => {
  const [config, data] = args
  const [translate, setTranslate] = useState<number | null>(null)

  const [tem, updateTemporary] = useTemporary({
    config,
    data
  })
  config.enableDrag && updateTemporary({ config, data })

  const events = useMemo((): DraggerResult['events'] => {
    let darg = false
    let startTranslate = 0
    let start = 0
    let move = 0
    let step = 0
    let translateWin = 0
    const end = () => {
      if (!darg) {
        return
      }
      darg = false
      // 由于使用 drag 前后要加一个因此需要减 1
      const base = startTranslate + move
      let index = Math.round(-base / step) - 1
      if (index === -1) {
        index = tem.config.length - 1
      } else if (index > tem.config.length - 1) {
        index = 0
      }
      setTranslate(null)
      tem.data.resume(index)
    }
    return {
      onMouseDown (e) {
        darg = true
        startTranslate = e.currentTarget.getBoundingClientRect().x
        step = e.currentTarget.children[0].scrollWidth
        start = e.clientX
        translateWin = step * tem.config.length
        tem.data.pause()
        setTranslate(startTranslate)
        e.preventDefault()
      },
      onMouseUp: end,
      onMouseLeave: end,
      onMouseMove (e) {
        if (!darg) {
          return
        }
        move = e.clientX - start
        let value = startTranslate + move
        const halfStep = step / 2
        if (value > -halfStep) {
          value -= translateWin
        } else if (value < -(translateWin + halfStep)) {
          value += translateWin
        }
        setTranslate(value)
      }
    }
  }, [tem])

  return {
    events: config.enableDrag ? events : {},
    translate
  }
}
