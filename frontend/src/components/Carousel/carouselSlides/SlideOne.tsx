import * as React from "react";
import styled from "styled-components";

const SContainer = styled.div`
  align-items: center;
  display: flex;
  position: relative;
  height: 100vh;
  width: 100vw;
  margin: 0 auto;
  overflow: hidden;
`;

const STextWrapper = styled.div`
  display: flex;
  flex-direction: column;
  margin: 5px 10px;
`;

const SlideOne = () => (
  <SContainer>
    <STextWrapper>
      <h1>
        Header 1
      </h1>
      <p>
        A short paragraph with some descriptive text.
      </p>
    </STextWrapper>
    <img src="assets/iphone.png" alt="" />
  </SContainer>
);

export default SlideOne;