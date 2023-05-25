import Carousel from './components/Carousel';
import imgAirpods from './assets/airpods.png'
import imgiPhone from './assets/iphone.png';
import imgTablet from './assets/tablet.png';
import './App.css';

function App() {
  return (
    <div className='app'>
      <Carousel className='app__carousel'>
        <div className='app__carousel-item'>
          <img className='app__carousel-img' src={imgiPhone} alt="iphone" />
          <h3 className='app__carousel-title app__carousel-title--white'>xPhone</h3>
          <p className='app__carousel-desc app__carousel-desc--white'>Lots to Love. Less to spend.</p>
          <p className='app__carousel-desc app__carousel-desc--white'>Starting at $399.</p>
        </div>
        <div className='app__carousel-item'>
          <img className='app__carousel-img' src={imgTablet} alt="tablet" />
          <h3 className='app__carousel-title'>Tablet</h3>
          <p className='app__carousel-desc'>Just the right amount of everything.</p>
        </div>
        <div className='app__carousel-item'>
          <img className='app__carousel-img' src={imgAirpods} alt="airpods" />
          <h3 className='app__carousel-title'>Buy a Tablet or xPhone for colledge.</h3>
          <h3 className='app__carousel-desc'>Get airPods.</h3>
        </div>
      </Carousel>
    </div>
  );
}

export default App;
