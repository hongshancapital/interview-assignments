import styled from 'styled-components'

type IViewport = {
  width: string
  height: string
}

type ISlides = {
  offset: string
  transitionDuration: number
}

export const Viewport = styled.div<IViewport>`
  width: ${props => props.width};
  height: ${props => props.height};
  overflow: hidden;
  position: relative;
`

export const Slides = styled.div<ISlides>`
  width: 100%;
  height: 100%;
  display: flex;
  transform: translateX(${props => props.offset});
  transition: transform ${props => props.transitionDuration}ms;
  will-change: transform;
  > * {
    flex-shrink: 0;
  }
`

export const ProgressContainer = styled.div`
  display: flex;
  position: absolute;
  left: 50%;
  bottom: 40px;
  transform: translateX(-50%);
`
