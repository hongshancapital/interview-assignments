import React, { createContext, useContext, useReducer, useEffect, useState }from "react";
import "./App.css";
import Carousel from "./components/Carousel";
import { AppProvider } from "./providers/AppProvider";

function App() {
  return (
    <AppProvider>
      <div className="App">
        <Carousel></Carousel>
      </div>
    </AppProvider>
  )
}

export default App;
