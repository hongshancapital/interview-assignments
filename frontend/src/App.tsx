import './App.css';
import Carousel from './carousel';
import { ReactNode } from 'react';

function App() {
  let CarouselList: Array<ReactNode> = Array(3).fill(null);
  // 自定义轮播图结构
  CarouselList[0] = (
    <div className="bg-container bg-iphone" key="bg-iphone">
      <div className="carousel-item-title">
        xPhone
      </div>
      <div className="carousel-item-subtitle">
        Lots to love. Less to spend.
        <br/>Starting at $399
      </div>
    </div>
  );
  CarouselList[1] = (
    <div className="bg-container bg-airpods" key="bg-airpods">
      <div className="carousel-item-title">
        Tablet
      </div>
      <div className="carousel-item-subtitle">
        Just the right amount of everything.
      </div>
    </div>
  );
  CarouselList[2] = (
    <div className="bg-container bg-tablet" key="bg-tablet">
      <div className="carousel-item-title">
        Buy a Tablet or xPhone for college.
        <br/>Get arPods.
      </div>
    </div>
  );
  
  return (
    <div className='App'>
      <Carousel width="100%" height="50%">
        { CarouselList }
      </Carousel>
      <Carousel width="100%" height="50%">
        { CarouselList }
      </Carousel>
    </div>
  );
}

export default App;
