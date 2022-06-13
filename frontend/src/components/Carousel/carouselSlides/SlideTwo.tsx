import * as React from 'react';
import styled from 'styled-components';
import tablet from './../../../assets/tablet.png';

const SContainer = styled.div`
  display: flex;
  min-height: 100vh;
  justify-content: space-evenly;
  align-items: center;
  flex-direction: column;
`;

const Content = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Title = styled.h1`
  font-size: 3.5em;
  text-align: center;
  color: black;
  margin: 0px;
`;

const TextContent = styled.p`
  font-size: 1.5em;
  text-align: center;
  color: black;
  line-height: 0;
`;

const Show = styled.img`
  width: 110px;
  height: 100px;
`;

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
