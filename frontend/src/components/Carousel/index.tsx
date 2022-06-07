import React from "react";
import styled from 'styled-components'
import {Gallery} from './gallery'

const Container = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
`

export const Carousel = ({ images } : {images: string[]}) => {
  return (
    <Container>
      <Gallery images={images} />
    </Container>
  );
}
