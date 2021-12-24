import styled from 'styled-components';

const CarouselContainer = styled.div`
  position: relative;
  width: ${(props: Record<string, any>) => props.width + 'px'};
  height: ${(props: Record<string, any>) => props.height + 'px'};
  overflow: hidden;
`
const CarouselWrap = styled.div`
  display: flex;
  height: 100%;
  width: 100%;
  transition: ${(props: Record<string, any>) => 'transform ' + props.speed + 'ms'};
`

const PaginationInner = styled.div`
  background-color: #ffffff;
  height: 100%;
  width: 100%;
  transform: translate3d(-100%, 0, 0);
  transition: ${(props: Record<string, any>) => 'transform ' + props.delay + 'ms linear'};
`

export { CarouselContainer, CarouselWrap, PaginationInner }