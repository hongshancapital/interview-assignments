import styled from 'styled-components';

import { State, getTranslateX } from './helper';

export const CarouselRoot = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
`;

export const CarouselContainer = styled.div`
  position: relative;
  overflow: hidden;
  width: 100%;
  height: 100%;
`;

export interface ItemContainerProps {
  duration: number;
  state: State;
}

export const ItemContainer = styled.div<ItemContainerProps>`
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  transition: transform ${(props) => props.duration}ms ease-in-out;
  transform: translateX(${(props) => getTranslateX(props.state)}) scale(1);
`;
