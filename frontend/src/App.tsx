import './App.css';
import { Carousel } from './components/Carousel';
import { APP_CAROUSEL_ITEMS, CarouselItem } from './components/CarouselItem';

function App() {
  return (
    <div className="App">
      <Carousel>
        {APP_CAROUSEL_ITEMS.map((item) => (
          <CarouselItem key={item.title} {...item} />
        ))}
      </Carousel>
    </div>
  );
}

export default App;
