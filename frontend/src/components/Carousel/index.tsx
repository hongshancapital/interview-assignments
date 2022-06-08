import React, {useState, useEffect, useCallback} from "react";
import styled from 'styled-components'
import {Gallery} from './gallery'
import {Indicators} from './indicators'

const Container = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
`

export const Carousel = ({
  images,
  duration = 3
} : {
  images: string[]
  duration?: number
}) => {
  const [currentIndex, setCurrentIndex] = useState(0)

  const count = images.length

  useEffect(() => {
    setCurrentIndex(0)
  }, [count, duration])

  useEffect(() => {
    if (count > 0) {
      const timer = setTimeout(() => {
        setCurrentIndex((i) => (i + 1) % count)
      }, duration * 1000)

      return () => {
        clearTimeout(timer)
      }
    }
  }, [currentIndex])

  const handleClickIndicator = useCallback((index: number) => {
    setCurrentIndex(index)
  }, [])

  return (
    <Container>
      <Gallery
        images={images}
        currentIndex={currentIndex}
      />
      <Indicators
        count={count}
        currentIndex={currentIndex}
        duration={duration}
        onClickIndicator={handleClickIndicator}
      />
    </Container>
  );
}
