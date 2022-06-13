import * as React from 'react';
import styled from 'styled-components';
import airpods from '../../../../src/assets/airpods.png';

const SContainer = styled.div`
  display: flex;
  min-height: 100vh;
  justify-content: space-evenly;
  align-items: center;
  background-color: #f4f2f6;
  flex-direction: column;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Title = styled.h2`
  font-size: 3.5em;
  text-align: center;
  color: black;
  margin: 0px;
`;

const TextContent = styled.h2`
  font-size: 3.5em;
  text-align: center;
  color: black;
  line-height: 0;
`;

const Show = styled.img`
  width: 110px;
  height: 100px;
`;

const SlideThree = () => (
  <SContainer>
    <Content>
      <Title>Buy a Tablet or xPhone for college.</Title>
      <TextContent>Get arPods. </TextContent>
    </Content>
    <Show src={airpods} alt=""></Show>
  </SContainer>
);

export default SlideThree;
