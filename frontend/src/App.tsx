import React, { CSSProperties } from 'react';
import './App.css';
import Carousel from './Components/Carousel';
import GoodsItem from './Components/GoodsItem/index';
import { dataSource } from './Components/GoodsItem/config';

function App() {
  return (
    <div className="App">
      <Carousel duration={3000}>
        {dataSource.map(data => (
          <GoodsItem {...data} key={data.styles?.backgroundImage}></GoodsItem>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
