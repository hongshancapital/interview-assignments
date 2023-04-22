import './App.css';
import Carousel from './ components/carousel';

function App() {
  const images = [require("./assets/airpods.png"), require("./assets/iphone.png"), require("./assets/tablet.png")]
  return <div className='App'>
    <Carousel images={images} intervalTime={2000} />
  </div>;
}

export default App;
