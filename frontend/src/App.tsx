import React from "react";
import "./App.css";
import { Carousel } from "./pages/components/Carousel/Carousel";
import { ProductList } from "./pages/components/Carousel/const"

function App() {
  return <div className="App">
    <Carousel dataList={ProductList}/>
    </div>;
}

export default App;
