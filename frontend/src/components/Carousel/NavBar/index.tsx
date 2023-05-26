import styled from "styled-components";
import {CarouselDirection} from "../type";

const getNavBarStyle = (props:{ $direction: CarouselDirection }) => {
  if (props.$direction === CarouselDirection.Horizontal) {
    return `
      left: 0;
      right: 0;
      bottom: 60px;
      flex-direction: row;
      justify-content: center;
      align-items: center;
      margin: auto;
    `;
  }
  if (props.$direction === CarouselDirection.Vertical) {
    return `
      top: 0;
      bottom: 0;
      right: 60px;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      margin: auto;
    `
  }
  return ``;
}

const getNavItemStyle = (props:{ $direction: CarouselDirection }) => {
  if (props.$direction === CarouselDirection.Horizontal) {
    return `
      width: 40px;
      height: 3px;
      margin: 0 10px;
    `;
  }
  if (props.$direction === CarouselDirection.Vertical) {
    return `
      width: 3px;
      height: 40px;
      margin: 10px 0;
    `;
  }
  return ``;
}

const NavBar = styled.div< {$direction: CarouselDirection} >`
  position: absolute;
  display: flex;
  ${getNavBarStyle};
`

const NavItem = styled.div<{ $direction: CarouselDirection; $active: boolean; $duration: number; $autoplay: boolean }>`
  position: relative;
  overflow: hidden;
  background-color: #A9A9A9;
  border-radius: 3px;
  ${getNavItemStyle};

  @keyframes activeX {
    0% {
      transform: translateX(0);
    }
    100% {
      transform: translateX(100%);
    }
  }
  
  @keyframes activeY {
    0% {
      transform: translateY(0);
    }
    100% {
      transform: translateY(100%);
    }
  }

  &::before {
    content: '';
    top: ${props => props.$direction === CarouselDirection.Horizontal ? 0 : '-100%'};
    left: ${props => props.$direction === CarouselDirection.Horizontal ? '-100%' : 0};
    position: absolute;
    width: 100%;
    height: 100%;
    background-color: #fff;
    ${props => props.$active && !props.$autoplay && props.$direction === CarouselDirection.Horizontal ? 'transform: translateX(100%);' : ''};
    ${props => props.$active && props.$autoplay && props.$direction === CarouselDirection.Vertical ? 'transform: translateY(100%);' : ''};
    ${props => props.$active && props.$autoplay && props.$direction === CarouselDirection.Horizontal ? 'animation: activeX ' + props.$duration + 'ms linear' : ''};
    ${props => props.$active && props.$autoplay && props.$direction === CarouselDirection.Vertical ? 'animation: activeY ' + props.$duration + 'ms linear' : ''};
  }
`;

interface Props<T> {
  direction: CarouselDirection;
  data: ReadonlyArray<T>;
  keyExtractor(item: T, index: number): string | number;
  autoplay: boolean;
  scrolling: boolean;
  autoplayDelay: number;
  finishAnimation: () => void;
  current: number;
}

export default function NavBarComponent<T>(props: Props<T>) {
  const { direction, data, current, scrolling, autoplay, autoplayDelay, keyExtractor, finishAnimation } = props;
  return <NavBar $direction={direction}>
    {data.map((item, index) => (
      <NavItem
        key={keyExtractor(item, index)}
        $direction={direction}
        $autoplay={autoplay}
        $active={!scrolling && current === index}
        $duration={autoplayDelay}
        onAnimationEndCapture={finishAnimation}
      />
    ))}
  </NavBar>
}