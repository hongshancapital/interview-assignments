import { useEffect, useState } from 'react'
import { dotPluginFactory } from './plugin'
import { useOnce } from '../../utils/share'
import { DotProps, DotResult, DraggerProps, DraggerResult } from './type'

export const useDot = (props: DotProps): DotResult => {
  const [progress, setProgress] = useState(0)
  const data = useOnce(() => {
    const { plugin, data } = dotPluginFactory({
      onChange: setProgress,
      enableDot: props.enableDot,
      frameTime: 50
    })
    props.carousel.addHooks(plugin)
    return data
  })

  useEffect(() => {
    data.setEnableDot(props.enableDot)
    return data.clean
  }, [props.enableDot, data])
  return {
    getProgress (index) {
      return index === data.getCurrent() ? progress : 0
    }
  }
}

export const useDragger = (props: DraggerProps): DraggerResult => {
  const [translate, setTranslate] = useState<number | null>(null)
  const data = useOnce((): DraggerResult['events'] => {
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
      if (index === -1 || index > props.carousel.last) {
        index = index === -1 ? props.carousel.last : 0
      }
      setTranslate(null)
      props.carousel.resume(index)
    }
    return {
      onMouseDown (e) {
        darg = true
        startTranslate = e.currentTarget.getBoundingClientRect().x
        step = e.currentTarget.children[0].scrollWidth
        start = e.clientX
        translateWin = step * (props.carousel.last + 1)
        props.carousel.pause()
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
  })
  return {
    events: data,
    translate
  }
}
