import React from "react";
import "./App.scss";

import { Carousel } from "./components/Carousel";
import { defaultData } from './components/Carousel/data'

function App() {
  return <div className="App"><Carousel data={defaultData} /></div>;
}

export default App;
