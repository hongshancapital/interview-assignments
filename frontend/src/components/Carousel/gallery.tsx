import React from "react";
import styled from 'styled-components'

const Container = styled.div<{count: number}>`
  position: absolute;
  width: ${props => (props.count * 100) + '%'};
  height: 100%;
  top: 0;
  left: 0;
  overflow: visible;
`

const Picture = styled.img<{total: number}>`
  display: inline-block;
  width: ${props => (100.0 / props.total) + '%'};
  height: 100%;
  box-sizing: border-box;
  object-fit: cover;
`

export const Gallery = ({ images }: {
  images: string[]
}) => {
  const count = images.length
  return (
    <Container count={count}>
      {
        images.map((url) => <Picture total={count} src={url} key={url.slice(-20)} />)
      }
    </Container>
  )
}
