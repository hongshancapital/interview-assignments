import * as React from 'react';
import styled from 'styled-components';
import tablet from './../../../assets/tablet.png';

const SContainer = styled.div`
  display: flex;
  min-height: 100vh;
  justify-content: flex-start;
  align-items: center;
  overflow: hidden;
  flex-direction: column;
  margin-top: 10%;
`;

const Title = styled.h1`
  font-size: 3.5em;
  text-align: center;
  color: black;
  margin: 0px;
`;

const Content = styled.p`
  font-size: 1.5em;
  text-align: center;
  color: black;
  line-height: 0;
`;

const Show = styled.img`
  width: 110px;
  height: 100px;
  margin-top:18%;
`;

const SlideTwo = () => (
  <SContainer>
    <Title>Tablet</Title>
    <Content>Just the right amount of everything. </Content>
    <Show src={tablet} alt=""></Show>
  </SContainer>
);

export default SlideTwo;
