import React from "react";
import styled from 'styled-components'

const Container = styled.div`
  display: flex;
  justify-content: center;
  position: absolute;
  width: 100%;
  bottom: 3vh;
`

const Item = styled.span<{highlight: boolean}>`
  display: inline-block;
  margin: 0 5px;
  width: 40px;
  height: 6px;
  background-color: ${props => props.highlight ? '#ddd' : '#aaa'};
`

export const Indicators = ({ count, currentIndex }: {
  count: number
  currentIndex: number
}) => {
  const arr = Array.from(Array(count).keys())
  return (
    <Container>
      {
        arr.map((index) =>
          <Item
            highlight={currentIndex === index}
            key={index}
          />)
      }
    </Container>
  )
}
