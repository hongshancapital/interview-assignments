import { useState, useEffect, ReactElement } from 'react'

function useCarousel(
  items: Array<{ content: ReactElement, bgCover: string }>,
  duration: number = 1000,
): {
  activeIndex: number,
  pass: number,
  duration: number,
  pause: (itemIndex: number) => void,
  resume: (itemIndex: number) => void,
  select: (itemIndex: number) => void,
} {
  const [ activeIndex, setActiveIndex ] = useState(0)
  const [ paused, setPaused ] = useState(false)
  const [ pass, setPass ] = useState(0)

  useEffect(() => {
    const now = Date.now()
    const timer = setTimeout(() => {
      if(!paused && items && items.length) {
        const _pass = pass + Date.now() - now
        if(_pass < duration) {
          setPass(_pass)
        } else {
          setPass(0)
          setActiveIndex((activeIndex + 1) % items.length)
        }
      }
    }, 16)
    return () => {
      clearTimeout(timer)
    }
  }, [ activeIndex, duration, paused, pass, items ])

  return {
    activeIndex,
    pass,
    duration,
    pause(itemIndex: number) {
      itemIndex === activeIndex && setPaused(true)
    },
    resume(itemIndex: number) {
      itemIndex === activeIndex && setPaused(false)
    },
    select(itemIndex: number) {
      if(itemIndex !== activeIndex) {
        setPass(0)
        setPaused(true)
        setActiveIndex(itemIndex)
      }
    }
  }
}

export default useCarousel
