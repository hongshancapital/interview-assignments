import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import { Carousel } from './components/Carousel';
// 考虑到页面的结构可能各不相同，因此每个页面看作单独的组件
import Page1 from './components/pages/Page1';
import Page2 from './components/pages/Page2';
import Page3 from './components/pages/Page3';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <React.StrictMode>
    <Carousel>
      <Page1 />
      <Page2 />
      <Page3 />
    </Carousel>
  </React.StrictMode>
);
