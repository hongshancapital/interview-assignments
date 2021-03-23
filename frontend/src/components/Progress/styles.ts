import styled from 'styled-components'

type IInner = {
  offset: string
  duration: number
}

export const Outer = styled.div`
  width: 50px;
  height: 2px;
  background-color: grey;
  overflow: hidden;
  &:not(:last-child) {
    margin-right: 10px;
  }
`

export const Inner = styled.div<IInner>`
  width: 100%;
  height: 100%;
  background-color: #fff;
  transform: translateX(${props => props.offset});
  transition: transform ${props => props.duration}ms linear;
`
