import Carousel from './components/carousel';
import Iphone from './assets/iphone.png';
import Tablet from './assets/tablet.png';
import AirPods from './assets/airpods.png';
import './App.css';

function App() {
  return (
    <div className='App'>
      <Carousel>
        <div className='carousel-item carousel-item-iphone' style={{backgroundImage:`url(${Iphone})`}}>
          <div className='carousel-item-content'>
            <span className='carousel-item-title'>xPhone</span>
            <span className='carousel-item-des'>Lots to love. Less to spend.</span>
            <span className='carousel-item-des'>Starting at $399.</span>
          </div>
        </div>
        <div className='carousel-item carousel-item-tablet' style={{backgroundImage:`url(${Tablet})`}}>
          <div className='carousel-item-content'>
            <span className='carousel-item-title'>Tablet</span>
            <span className='carousel-item-des'>Just the right amount of everything.</span>
          </div>
        </div>
        <div className='carousel-item carousel-item-airPods' style={{backgroundImage:`url(${AirPods})`}}>
          <div className='carousel-item-content'>
            <span className='carousel-item-title'>Buy a Tablet or Xphone for colleage.</span>
            <span className='carousel-item-title'>Get airPods.</span>
          </div>
        </div>
      </Carousel>
    </div>
  );
}

export default App;
