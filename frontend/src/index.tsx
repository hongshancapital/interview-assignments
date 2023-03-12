import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

// react 18 开发环境在React.StrictMode 下 useEffect 会调用两次
// link: https://reactjs.org/docs/strict-mode.html#ensuring-reusable-state
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
