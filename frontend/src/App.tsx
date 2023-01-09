import './App.css';
import Carousel, { carouselMockData } from './components/Carousel';

function App() {
  return (
    <div className='App'>
      <Carousel contents={carouselMockData} />
    </div>
  );
}

export default App;
