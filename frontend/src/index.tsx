import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
// import FormDemo from './FormDemo';
import { WithUserinfoDemo } from './HocDemo';

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement,
);
root.render(
  <React.StrictMode>
    <App />
    {/* <FormDemo /> */}
    <WithUserinfoDemo />
    <div style={{ height: 300 }}></div>
  </React.StrictMode>,
);
