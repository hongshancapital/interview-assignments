import React from "react";
import styled, {keyframes} from 'styled-components'

const Container = styled.div`
  display: flex;
  justify-content: center;
  position: absolute;
  left: 10%;
  width: 80%;
  bottom: 3%;
  overflow: hidden;
`

const Item = styled.span`
  display: inline-block;
  margin: 0 5px;
  width: 50px;
  height: 3px;
  background-color: #aaa;
  border-radius: 3px;
  overflow: hidden;
`

const move = keyframes`
  from {
    transform: translateX(-100%);
  }

  to {
    transform: translateX(0);
  }
`;

const ProgressBar = styled.div<{duration: number}>`
  width: 100%;
  height: 100%;
  background-color: #f9f9f9;
  animation: ${move} linear forwards;
  animation-duration: ${props => props.duration + 's'};
`

export const Indicators = ({
  count,
  currentIndex,
  duration,
}: {
  count: number
  currentIndex: number
  duration: number
}) => {
  const arr = Array.from(Array(count).keys())
  return (
    <Container>
      {
        arr.map((index) =>
          <Item key={index}>
            {
              currentIndex === index ?
                <ProgressBar duration={duration} /> :
                null
            }
          </Item>
        )
      }
    </Container>
  )
}