import * as React from "react";
import styled from "styled-components";
import airpods from '../../../../src/assets/airpods.png'

const SContainer = styled.div`
  display: flex;
  min-height: 100vh;
  justify-content: flex-start;
  align-items: center;
  background-color: #F4F2F6;
  flex-direction: column;
`;

const Content = styled.div`
  margin-top: 10%;
`

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
  margin-top:33%;
`;

const SlideThree = () => (

  <SContainer>
      <Content>
      <Title>Buy a Tablet or xPhone for college.</Title>
      <TextContent>Get arPods. </TextContent>
      <Show src={airpods} alt=""></Show>
      </Content>
  </SContainer>
);

export default SlideThree;