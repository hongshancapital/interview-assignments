import * as React from 'react';
import styled from 'styled-components';
import iphone from '../../../../src/assets/iphone.png';
import airpods from '../../../../src/assets/airpods.png'
import * as images from '../../../src/assets/*.png'

const SContainer = styled.div`
  align-items: center;
  display: flex;
  background-image: url(${iphone})
`;

const STextWrapper = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
  margin: 5px 10px;
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

const SlideOne = () => (
  <SContainer>
    <STextWrapper>
      <Title>xPhone</Title>
      <Content>Lots to love.Less to spend. </Content>
      <Content>Starting at $399.</Content>
    </STextWrapper>
    
  </SContainer>
);

export default SlideOne;
