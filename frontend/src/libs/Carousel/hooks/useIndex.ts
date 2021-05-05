import { ReactNode, useEffect, useState } from 'react'

const useIndex = ({ duration, children }: { duration: number, children: ReactNode[] }) => {
  const [currIndex, setCurrIndex] = useState(0)

  useEffect(() => {
    const id = setInterval(() => {
      setCurrIndex((currIndex) =>
        currIndex >= children.length - 1 ? 0 : currIndex + 1
      )
    }, duration)

    return () => {
      clearInterval(id)
    }
  }, [duration, children])


  return { currIndex }
}

export default useIndex