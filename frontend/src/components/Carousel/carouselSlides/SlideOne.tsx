import * as React from 'react';
import styled from 'styled-components';
import iphone from '../../../../src/assets/iphone.png';

const getWidth = () => window.innerWidth

const SContainer = styled.div`
  display: flex;
  min-height: 100vh;
  justify-content: flex-start;
  align-items: center;
  background-color: #090909;
  flex-direction: column;
`;

const Content = styled.div`
  margin-top: 10%;
  width: ${getWidth()};
`

const STextWrapper = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
`;

const Title = styled.h1`
  font-size: 3.5em;
  text-align: center;
  color: white;
`;

const TextContent = styled.p`
  font-size: 1.5em;
  text-align: center;
  color: white;
  line-height: 0;
`;

const Icon = styled.img`
  width: 110px;
  height: 100px;
  margin-top:35%;
`

const SlideOne = () => (
  <SContainer>
    <Content>
    <STextWrapper>
      <Title>xPhone</Title>
      <TextContent>Lots to love.Less to spend. </TextContent>
      <TextContent>Starting at $399.</TextContent>
    </STextWrapper>
    <Icon src={iphone} alt="" />
    </Content>
  </SContainer>
);

export default SlideOne;
