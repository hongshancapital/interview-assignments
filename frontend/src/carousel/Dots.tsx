import { useCallback } from "react";
import styled from "styled-components";

interface IDotsProps {
  count: number;
  duration: number;
  selectedIndex: number;
}

const DOT_WIDTH = 60;

const StyledDots = styled.div<{ duration: number }>`
  display: flex;
  justify-content: center;
  position: absolute;
  width: 100%;
  left: 0;
  bottom: 30px;
  text-align: center;

  .dot {
    background-color: #acacac;
    height: 3px;
    width: ${DOT_WIDTH}px;
    margin: 0 6px;
    border-radius: 2px;
    cursor: pointer;

    .active {
      background-color: white;
      height: 3px;
      animation: ${(props) => `progress ${props.duration}ms linear`};
    }
  }

  @keyframes progress {
    0% {
      width: 0px;
    }
    100% {
      width: ${DOT_WIDTH}px;
    }
  }
`;

export const Dots: React.FC<IDotsProps> = (props) => {
  const { count, duration, selectedIndex } = props;
  const renderDot = useCallback(
    (index: number) => {
      const isActive =
        index === selectedIndex || (selectedIndex === count && index === 0);
      return (
        <div key={index} className="dot">
          <div className={isActive ? "active" : ""} />
        </div>
      );
    },
    [selectedIndex]
  );

  return (
    <StyledDots duration={duration}>
      {new Array(count).fill(0).map((_value, index) => {
        return renderDot(index);
      })}
    </StyledDots>
  );
};
