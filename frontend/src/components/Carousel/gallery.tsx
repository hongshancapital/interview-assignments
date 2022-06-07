import React from "react";
import styled from 'styled-components'

const Container = styled.div<{
  count: number
  currentIndex: number
}>`
  position: absolute;
  width: ${props => (props.count * 100) + '%'};
  height: 100%;
  top: 0;
  left: 0;
  transform: ${({count, currentIndex}) => {
    const offsetPercent = count > 0 ? (-100.0 * currentIndex / count) : 0
    return 'translateX(' + offsetPercent + '%)'
  }};
  transition: 0.5s;
`

const Picture = styled.img<{total: number}>`
  display: inline-block;
  width: ${props => (100.0 / props.total) + '%'};
  height: 100%;
  box-sizing: border-box;
  object-fit: cover;
`

export const Gallery = ({ images, currentIndex }: {
  images: string[]
  currentIndex: number
}) => {
  const count = images.length
  return (
    <Container count={count} currentIndex={currentIndex}>
      {
        images.map((url) =>
          <Picture
            src={url}
            total={count}
            key={url.slice(-20)}
          />)
      }
    </Container>
  )
}
