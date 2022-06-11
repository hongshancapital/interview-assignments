import * as React from "react";
import styled from "styled-components";

const SContainer = styled.div`
  align-items: center;
  display: flex;
`;

const STextWrapper = styled.div`
  display: flex;
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
      <h1>
        Header 2
      </h1>
      <p>
        A short paragraph with some descriptive text.
      </p>
    </STextWrapper>
    <img src="../../assets/tablet.png" alt=""/>
  </SContainer>
);

export default SlideOne;