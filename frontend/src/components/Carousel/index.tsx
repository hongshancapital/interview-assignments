import React, {useState, useEffect} from "react";
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

  useEffect(() => {
    setCurrentIndex(0)

    if (images.length > 0) {
      const timer = setInterval(() => {
        setCurrentIndex((i) => (i + 1) % images.length)
      }, duration * 1000)
  
      return () => {
        clearInterval(timer)
      }
    }
   
  }, [images.length, duration])

  return (
    <Container>
      <Gallery images={images} currentIndex={currentIndex} />
      <Indicators count={images.length} currentIndex={currentIndex} duration={duration} />
    </Container>
  );
}
