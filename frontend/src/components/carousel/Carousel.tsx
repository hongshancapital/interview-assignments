import CarouselWrap from './CarouselWrap'
import React, { useMemo } from 'react'
interface CarouselProps {
  children: ValidReactChild | ValidReactChild[]
}

const Carousel: React.FC<CarouselProps> = ({ children }) => {
  const validChildren = useMemo(() => {
    const childrenOfArray = React.Children.toArray(children)
    return childrenOfArray.filter((child) => {
      if (typeof child === 'string') {
        return !!child.trim()
      }
      return !!child
    })
  }, [children])

  return (
    <>
      <CarouselWrap>{validChildren}</CarouselWrap>
    </>
  )
}

export default Carousel
