import * as React from 'react';
import styled from 'styled-components';
import iphone from '../../../../src/assets/iphone.png';

// 子组件幻灯片包容器DIV组件
const SContainer = styled.div`
  display: flex;
  min-height: 100vh;
  justify-content: space-evenly;
  align-items: center;
  background-color: #090909;
  flex-direction: column;
`;

// 所有文本的容器
const STextWrapper = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
`;

// 文本中标题的容器
const Title = styled.h1`
  font-size: 3.5em;
  text-align: center;
  color: white;
  margin-bottom: 1px;
`;

// 文本中内容的容器
const TextContent = styled.p`
  font-size: 1.5em;
  text-align: center;
  color: white;
  line-height: 0;
  margin-bottom: 5px;
`;

// 图标的容器
const Icon = styled.img`
  width: 110px;
  height: 100px;
`;


/**
 * @Description 第一个幻灯片子组件
 * @date 2022-06-13
 * @returns {JSX.Element}
 */
const SlideOne = () => (
  <SContainer>
    <STextWrapper>
      <Title>xPhone</Title>
      <TextContent>Lots to love.Less to spend. </TextContent>
      <TextContent>Starting at $399.</TextContent>
    </STextWrapper>
    <Icon src={iphone} alt="" />
  </SContainer>
);

export default SlideOne;
