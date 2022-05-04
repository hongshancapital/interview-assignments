import React from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App'
const rootElement = document.getElementById('root')
const root = createRoot(rootElement as HTMLElement)

root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
)
