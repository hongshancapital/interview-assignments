import CarouselWrap from './CarouselWrap'
import React, { useMemo } from 'react'
import { CarouselConfigs } from './types'
interface CarouselProps extends CarouselConfigs {
  children: ValidReactChild | ValidReactChild[]
}

const Carousel: React.FC<CarouselProps> = ({ children, ...rest }) => {
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
      <CarouselWrap {...rest}>{validChildren}</CarouselWrap>
    </>
  )
}

export default Carousel
