import React, { ReactElement } from "react"
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

const Item = styled.div<{count: number}>`
  display: inline-block;
  position: relative;
  width: ${props => (100.0 / props.count) + '%'};
  height: 100%;
  box-sizing: border-box;
  overflow: hidden
`

export const Content = ({ currentIndex, children }: {
  currentIndex: number
  children: ReactElement[]
}) => {
  const count = children.length
  return (
    <Container count={count} currentIndex={currentIndex}>
      {
        children.map((child, index) =>
          <Item count={count} key={index}>
            {child.props.children}
          </Item>
        )
      }
    </Container>
  )
}
