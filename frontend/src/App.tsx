import './App.css';
import Carousel, { carouselMockData } from './components/Carousel';

function App() {
  return (
    <div className='App'>
      <Carousel contents={carouselMockData} interval={3} duration={1} />
    </div>
  );
}

export default App;
