import Swiper from './Slider/ui'
import './App.css';

function App() {
  const imgList = [
    {
      src: require('./images/demo1.jpeg'),
      alt: 'images-1',
    },
    {
      src: require('./images/demo2.jpeg'),
      alt: 'images-2',
    },
    {
      src: require('./images/demo3.jpeg'),
      alt: 'images-3',
    },
    <div>this is a react Component Dom</div>
  ];
  return (
    <div className="App">
      <Swiper
        sliderList={imgList}
        dots={true}
        speed={1000}
        delay={3000}
      ></Swiper>
    </div>
  );
}

export default App;
