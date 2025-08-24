import Carousel from './components/Carousel/Carousel';
import CarouselItem from './components/Carousel/CarouselItem';

import iphoneImg from './assets/iphone.png'
import tabletImg from './assets/tablet.png'
import airpodsImg from './assets/airpods.png'

import './App.css';

function App() {
  return <div className='App'>
    <Carousel autoplay interval={3000}>
      <CarouselItem>
        <div className="item text text-white" style={{backgroundImage: `url(${iphoneImg})`}}>
          <div className="title">xPhone</div>
          <div>lots to love, less to spend.</div>
          <div>Starting at $399.</div>
        </div>
      </CarouselItem>

      <CarouselItem>
        <div className="item text" style={{backgroundImage: `url(${tabletImg})`}}>
          <div className="title">Tablet</div>
          <div>Just the right amount of everything</div>
        </div>
      </CarouselItem>

      <CarouselItem>
        <div className="item text" style={{backgroundImage: `url(${airpodsImg})`}}>
          <div className="title">Buy a Tablet or xPhone for college.</div>
          <div className="title">Get arPods</div>
        </div>
      </CarouselItem>
    </Carousel>
  </div>;
}

export default App;
