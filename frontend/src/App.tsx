import React from "react";
import "./App.css";
import Carousel from "./components/Carousel"
import CommonProductPage, { DemoData } from './pages/CommonProductPage'

function App() {

  return <div className="App">
    <Carousel
        interval={3}
    >
      {
        DemoData.map((data, index) => {
          return <CommonProductPage
              data={data}
              key={index}
          />
        })
      }
    </Carousel>
  </div>;
}

export default App;
