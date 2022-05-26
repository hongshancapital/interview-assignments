import { RefObject, useState, useEffect } from 'react'

type Tfunc = ($dom: RefObject<HTMLElement>, callbacks: {
  onTransitionStart?: () => void,
  onTransitionEnd?: () => void,
}, dependencies?: any[]) => boolean

const useTransitionEvent: Tfunc = ($dom, callbacks, dependencies = []) => {
  const { onTransitionStart, onTransitionEnd } = callbacks
  const [loading, setLoading] = useState(false)
  const onStart = () => {
    setLoading(true)
    onTransitionStart && onTransitionStart()
  }
  const onEnd = () => {
    setLoading(false)
    onTransitionEnd && onTransitionEnd()
  }
  useEffect(() => {
    const { current } = $dom
    if (current) {
      current.addEventListener('transitionstart', onStart)
      current.addEventListener('transitionend', onEnd)
    }
    return function () {
      if (current) {
        current.removeEventListener('transitionstart', onStart)
        current.removeEventListener('transitionend', onEnd)
      }
    }
  }, [$dom.current, ...dependencies])
  return loading
}

export default useTransitionEvent