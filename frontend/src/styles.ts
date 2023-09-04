import styled from 'styled-components';
import { IProps } from './Carousel';
const CarouselContainer = styled.div`
  position: relative;
  width: ${(props: IProps) => props.width + 'px'};
  height: ${(props: IProps) => props.height + 'px'};
  overflow: hidden;
`
const CarouselWrap = styled.div`
  display: flex;
  height: 100%;
  width: 100%;
  transition: ${(props: IProps) => 'transform ' + props.speed + 'ms'};
`

const PaginationInner = styled.div`
  background-color: #ffffff;
  height: 100%;
  width: 100%;
  transform: translate3d(-100%, 0, 0);
  transition: ${(props: IProps) => 'transform ' + props.delay + 'ms linear'};
`

export { CarouselContainer, CarouselWrap, PaginationInner }