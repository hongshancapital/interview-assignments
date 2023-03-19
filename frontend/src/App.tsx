import './App.css';
import Carousel from './Carousel';
import carouselItems from './carouselItems';


function App() {
  return <div className='App'>
    <Carousel autoPlay={false} autoPlayGap={3000} className="demo-carousel" items={carouselItems} />
  </div>;
}

export default App;
