import { useState, useEffect } from 'react' 
function useCarousel(
  items: object[],
  duration: number = 1000,
): {
  activeIndex: number,
  pass: number,
  duration: number
} {
  const [ activeIndex, setActiveIndex ] = useState(0)
  const [ pass, setPass ] = useState(0)
  useEffect(() => {
    const now = Date.now()
    const timer = setTimeout(() => {
      if(items && items.length) {
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
  }, [ activeIndex, duration, pass, items])

  return {
    activeIndex,
    pass,
    duration
  }
}

export default useCarousel
