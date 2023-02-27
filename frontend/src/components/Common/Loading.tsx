import React from 'react';
import styled from 'styled-components';
import { Spin } from 'antd';

// Loading局部样式
const Wrapper = styled.section`
  .loading {
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;
// 构建Loading组件
const Loading: React.FunctionComponent <{}> = () => (
  <Wrapper>
    <div
      className="loading"
      style={{ position: 'absolute', top: '50%', left: '50%', transform: 'translate(-50%, -50%)' }}
    >
      <Spin />
    </div>
  </Wrapper>
);
export default Loading;
