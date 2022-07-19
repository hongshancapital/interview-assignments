import React from "react";
import "./App.css";
import Carousel from './components/carousel'
import productList from "./const";

function App() {
  return <div className="App">
    <Carousel list={productList} />
    </div>;
}

export default App;
