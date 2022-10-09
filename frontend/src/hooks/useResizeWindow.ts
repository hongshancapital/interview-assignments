import { useState, useEffect } from 'react'

export const useResizeWindow = () => {
  const [screenWidth, setScreenWidth] = useState<number>(document.documentElement.clientWidth)

  const resetScreenWidth = () => {
    setScreenWidth(document.documentElement.clientWidth)
  }
  useEffect(() => {
    window.addEventListener('resize', resetScreenWidth)

    return () => {
      window.removeEventListener('resize', resetScreenWidth)
    }
  })

  return { screenWidth }
}
