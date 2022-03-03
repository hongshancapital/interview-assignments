import React from "react";
import "./App.css";
import Carousel from './components/Carousel'
import Demo from './components/Carousel/Demo'
import { data } from './resource'

function App() {
  return <div className="App">
    <Carousel interval={4000}>
      {
        data.map(item => (
          <Carousel.Item key={item.id}>
            <Demo {...item} />
          </Carousel.Item>
        ))
      }
    </Carousel>
  </div>;
}

export default App;
