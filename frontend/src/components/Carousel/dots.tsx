import React from 'react';
import styled from 'styled-components';
import MemoProgressBar from './processbar';

// 指示器接口：描述Dots组件接收的props类型
interface IDotsDiv {
  dots: JSX.Element[];
  activeDot?: number;
  autoPlay?: number;
}

// 指示器的总容器
const DotsDiv = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  bottom: 70px;
`;

/**
 * @Description 进度条总组件：根据传入的dots数组个数，确定进度条个数
 * @date 2022-06-13
 * @param {JSX.Element} {dots
 * @param {number} activeDot
 * @param {number} autoPlay}:IDotsDiv
 * @returns {JSX.Element[]}
 */
const Dots = ({ dots, activeDot, autoPlay }: IDotsDiv) => {

  return (
    <DotsDiv>
      {dots.map((dot, i) => (
        <MemoProgressBar
          key={dot.type.name}
          active={activeDot === i}
          autoPlay={autoPlay}
        />
      ))}
    </DotsDiv>
  );
};

export default Dots;
