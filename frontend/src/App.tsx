import './App.css';
import Carousel from './components/Carousel';
import CarouselCard from './views/CarouselCard';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

function App() {
  return (
    <div className="App">
      <Carousel autoplay>
        <CarouselCard backgroundColor="#111111" backgroundImage={iphone}>
          <div style={{ color: '#fff' }}>
            <h1 style={{ fontSize: 50 }}>xPhone</h1>
            <span style={{ fontSize: 30 }}>
              Lots to love. Less to spend.
              <br />
              Starting at $399.
            </span>
          </div>
        </CarouselCard>
        <CarouselCard backgroundColor="#FAFAFA" backgroundImage={tablet}>
          <h1 style={{ fontSize: 50 }}>Tablet</h1>
          <span style={{ fontSize: 30, fontWeight: 500 }}>
            Just the right amount of everything.
          </span>
        </CarouselCard>
        <CarouselCard backgroundColor="#F1F1F3" backgroundImage={airpods}>
          <h1 style={{ fontSize: 60 }}>
            Buy a Tablet or xPhone for college.
            <br />
            Get arPods
          </h1>
        </CarouselCard>
      </Carousel>
    </div>
  );
}

export default App;
