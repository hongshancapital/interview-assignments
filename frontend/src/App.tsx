import './App.css';
import Carousel from './carousel/Carousel'
import CarouselItem from './carousel/CarouselItem'

function App() {

  const config = {
    num: 3,
    duration: 5000
  }

  return (
    <div className='App'>
      <Carousel config={config}>
        <CarouselItem />
      </Carousel>
    </div>
  );
}

export default App;
