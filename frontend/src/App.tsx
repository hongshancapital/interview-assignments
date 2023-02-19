import './App.css';
import Carousel from './components/Carousel';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

const CarouselItem = Carousel.Item

function App() {
  return <div className='App'>
    <Carousel
      className="carousel"
      autoplay
    >
      <CarouselItem
        className="carousel_item"
        style={{ backgroundImage: `url(${iphone})` }}
      >
        <div className="content text_white">
          <h1 className='title'>xPhone</h1>
          <p className='text'>Lots to love. Less to spend.</p>
          <p className='text'>Starting as $399.</p>
        </div>
      </CarouselItem>
      <CarouselItem
        className="carousel_item"
        style={{ backgroundImage: `url(${tablet})` }}
      >
        <div className="content">
          <h1 className='title'>Tablet</h1>
          <p className='text'>Just the right amount of everything.</p>
        </div>
      </CarouselItem>
      <CarouselItem
        className="carousel_item"
        style={{ backgroundImage: `url(${airpods})` }}
      >
        <div className='content'> 
          <h1 className='title'>
            Buy a Tablet or xPhone for college.
            <br />
            Get airPods.
          </h1>
        </div>
      </CarouselItem>
    </Carousel>
  </div>;
}

export default App;
