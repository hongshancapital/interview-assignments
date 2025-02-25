import './App.css';
import CarouselPage from './src/Carousel';
import { sliderList } from './data';

function App() {
  return (
    <div className='App' data-testid="app">
      <CarouselPage sliderList={sliderList}></CarouselPage>
    </div>
  );
}

export default App;
