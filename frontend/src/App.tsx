import React from "react";
import "./App.css";
import Carousel from './Carousel'
import demoItems from './demo-items'

function App() {
  return (
    <div className="App">
      <Carousel items={demoItems}/>
    </div>
  )
}

export default App;
