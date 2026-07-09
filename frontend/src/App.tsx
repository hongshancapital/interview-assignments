import './App.css';
import Carousel from './Carousel';
import img1 from './assets/airpods.png';
import img2 from './assets/iphone.png';
import img3 from './assets/tablet.png';

const imgs = [img1, img2, img3];
function App() {
  return (
    <div className='App'>
      <Carousel>
        {imgs.map(img => (
          <img src={img} key={img} alt='' />
        ))}
      </Carousel>
    </div>
  );
}

export default App;
