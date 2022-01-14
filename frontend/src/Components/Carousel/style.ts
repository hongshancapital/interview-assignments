import style, { keyframes } from "styled-components";

export const CarouselWrapper = style.div`
    width: 100%;
    height: 100%;
    overflow: hidden;
    position: relative; 
`;

export const loopFrame = ({ total, current }: { total: number; current: number }) => {
  let startX = current ===  0 ? '-100%' : `-${((current - 1) / total) * 100}%`;
  let endX = `-${(current / total) * 100}%`;

  return keyframes`
    0% {transform: translate(${startX}, 0);}
    100% {transform: translate(${endX}, 0);}
  `;
};

export const LoopContainer = style.div<{
  total: number;
  current: number;
  slideSpeed: number;
}>`
    display: flex;
    height: 100%;
    width: ${(props) => props.total * 100 + "%"};
    animation-name: ${(props) =>
      loopFrame({ total: props.total, current: props.current })};
    animation-duration: ${(props) => props.slideSpeed + "ms"};
    animation-fill-mode:both;
`;

export const LoopItem = style.div<{ total: number }>`
  display: inline-block;
  height: 100%;
  width: ${props =>  (1 / props.total) * 100 + '%'};
`;

export const Indicator = style.div`
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  bottom: 20px;
  margin: auto;
  font-size: 0;
`;