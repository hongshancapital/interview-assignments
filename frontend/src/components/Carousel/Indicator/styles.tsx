import styled from 'styled-components';

export const IndicatorContainer = styled.div`
  position: absolute;
  bottom: 5%;
  left: 0;
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
`;

export const IndicatorItem = styled.div`
  margin: 0 5px;
  width: 60px;
  height: 4px;
  border-radius: 2px;
  cursor: pointer;
  background-color: #a9a9a9;
`;

interface ProgressBarProps {
  duration: number;
}

export const ProgressBar = styled.div<ProgressBarProps>`
  width: 100%;
  height: 100%;
  border-radius: 2px;
  background-color: #fff;
  animation: progress ${(props) => props.duration}ms linear;

  @keyframes progress {
    0% {
      width: 0%;
    }

    100% {
      width: 100%;
    }
  }
`;
