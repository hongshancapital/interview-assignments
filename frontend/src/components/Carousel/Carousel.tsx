import React, {useState, useEffect, useCallback, ReactElement} from "react"
import styled from 'styled-components'
import { CarouselSlide } from "./CarouselSlide"
import { Content } from './Content'
import { Indicators } from './indicators'

const Container = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
`

export const Carousel = ({
  children,
  duration = 3
} : {
  children: ReactElement | ReactElement[]
  duration?: number
}) => {
  const validChildren = (
    Array.isArray(children) ? children : [children]
  ).filter(
    (child) => child.type === CarouselSlide
  )

  const count = validChildren.length

  const [currentIndex, setCurrentIndex] = useState(0)

  useEffect(() => {
    setCurrentIndex(0)
  }, [count])

  useEffect(() => {
    if (count > 0) {
      const timer = setTimeout(() => {
        setCurrentIndex((currentIndex + 1) % count)
      }, duration * 1000)

      return () => {
        clearTimeout(timer)
      }
    }
  }, [currentIndex, count, duration])

  const handleClickIndicator = useCallback((index: number) => {
    setCurrentIndex(index)
  }, [])

  return (
    <Container>
      <Content currentIndex={currentIndex}>
        {validChildren}
      </Content>
      <Indicators
        count={count}
        currentIndex={currentIndex}
        duration={duration}
        onClickIndicator={handleClickIndicator}
      />
    </Container>
  );
}
