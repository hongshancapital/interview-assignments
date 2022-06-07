import React, {useState, useEffect} from "react";
import styled from 'styled-components'
import {Gallery} from './gallery'

const Container = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
`

export const Carousel = ({ images } : {images: string[]}) => {
  const [currentIndex, setCurrentIndex] = useState(0)

  useEffect(() => {
    setCurrentIndex(0)

    if (images.length > 0) {
      const timer = setInterval(() => {
        setCurrentIndex((i) => (i + 1) % images.length)
      }, 3000)
  
      return () => {
        clearInterval(timer)
      }
    }
   
  }, images)

  return (
    <Container>
      <Gallery images={images} currentIndex={currentIndex} />
    </Container>
  );
}
