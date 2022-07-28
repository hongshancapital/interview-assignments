import React from "react";
import "./App.css";
import Router from "./router/route";
import { HashRouter,BrowserRouter } from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <Router />
    </BrowserRouter>
  );
}

export default App;
