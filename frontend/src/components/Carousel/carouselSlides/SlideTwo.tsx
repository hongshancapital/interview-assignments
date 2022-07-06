import * as React from 'react';
import styled from 'styled-components';
import tablet from './../../../assets/tablet.png';

// 子组件幻灯片包容器DIV组件
const SContainer = styled.div`
  display: flex;
  min-height: 100vh;
  justify-content: space-evenly;
  align-items: center;
  flex-direction: column;
`;

// 所有内容的容器
const Content = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;
// 文本中标题的容器
const Title = styled.h1`
  font-size: 3.5em;
  text-align: center;
  color: black;
  margin: 0px;
`;

// 文本中内容的容器
const TextContent = styled.p`
  font-size: 1.5em;
  text-align: center;
  color: black;
  line-height: 0;
`;
// 图标的容器
const Show = styled.img`
  width: 110px;
  height: 100px;
`;

/**
 * @Description 第二个幻灯片子组件
 * @date 2022-06-13
 * @returns {JSX.Element}
 */
const SlideTwo = () => (
  <SContainer>
    <Content>
    <Title>Tablet</Title>
    <TextContent>Just the right amount of everything. </TextContent>
    </Content>
    <Show src={tablet} alt=""></Show>
  </SContainer>
);

export default SlideTwo;
