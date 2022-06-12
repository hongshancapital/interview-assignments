import * as React from 'react';
import styled from 'styled-components';
import iphone from '../../../../src/assets/iphone.png';

// const getWidth = () => window.innerWidth

const SContainer = styled.div`
  display: flex;
  min-height: 100vh;
  justify-content: center;
  align-items: center;
  background-color: #090909;
  flex-direction: column;
`;

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

const Content = styled.p`
  font-size: 1.5em;
  text-align: center;
  color: white;
  line-height: 0;
`;

const Icon = styled.img`
  width: 110px;
  height: 100px;
  margin-top:18%;
`

const SlideOne = () => (
  <SContainer>
    <STextWrapper>
      <Title>xPhone</Title>
      <Content>Lots to love.Less to spend. </Content>
      <Content>Starting at $399.</Content>
    </STextWrapper>
    <Icon src={iphone} alt="" />
  </SContainer>
);

export default SlideOne;
