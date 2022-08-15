import React from "react";
import "./App.css";
import Carousel from './component/Carousel'
import Product from './component/Product'
import productList from "./const";

function App() {
  return <div className="App">
    <Carousel>
      {productList.map(data => (
        <Carousel.Item>
          <Product {...data} />
        </Carousel.Item>
      ))}
    </Carousel>
    {/* write your component here */}</div>;
}

export default App;
