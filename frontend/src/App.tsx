import React from "react";
import Carousel from './components/Carousel';
import GoodsIntro from './components/GoodsIntro';
import { useGoodsDatas, IgoodsDatas } from './hooks/useGoodsDatas';

import "./App.css";

const CarouselItem = Carousel.CarouselItem;

function App() {
  const goodsDatas: IgoodsDatas[] = useGoodsDatas();

  return (
    <div className="App">
      <Carousel intervalTime={3000}>
        {goodsDatas.map((goodItem, index) => 
          <CarouselItem key={index}>
            <GoodsIntro goodsInfo = {goodItem}/>
          </CarouselItem>
        )}
        </Carousel>
    </div>
  )
}

export default App;
