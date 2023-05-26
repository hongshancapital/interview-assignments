import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  // 开发环境会导致useEffect调用两次
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
