
import styled from 'styled-components';

export const ContainerStyled = styled.div`
  overflow: hidden;
`;

export const ListStyled = styled.div<{ index: number; duration: number }>`
  display: flex;
  background-color: #fcfcfc;
  transform: ${(props) => `translateX(-${(props.index - 1) * 100}%)`};
  transition: ${(props) => `all ${props.duration}ms ease`};
`;

export const ItemStyled = styled.div`
  width: 100%;
  height: 100%;
  flex-shrink: 0;
`;

export const CtrlListStyled = styled.div`
  position: absolute;
  bottom: 0;
  display: flex;
  justify-content: center;
  width: 100%;
  height: 24px;
  z-index: 1;
`;

export const ProcessStyled = styled.span`
  margin: 4px;
  width: 40px;
  height: 4px;
  background-color: #c1c1c1;
  cursor: pointer;
  span {
    display: block;
    width: 0;
    height: 4px;
    background: #fff;
  }
`
