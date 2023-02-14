import './App.css';
import Carousel from './Carousel/Carousel';
import airpods from './assets/airpods.png';
import tablet from './assets/tablet.png';
import iphone from './assets/iphone.png';
function App() {
  return <div className='App'>
    <Carousel>
      <div className='iphone' style={{ backgroundImage: `url(${iphone})` }}>
        <div className='title'>xPhone</div>
        <div className='text'>Lots to love.Less to spend.<br />Starting at $399</div>
      </div>
      <div className='tablet' style={{ backgroundImage: `url(${tablet})` }}>
        <div className='title'>Tablet</div>
        <div className='text'>Just the right amount of everything.</div>
      </div>
      <div className='airpods' style={{ backgroundImage: `url(${airpods})` }}>
        <div className='title'>Buy  a Tablet or xPhone for college. <br /> Get airPods</div>
      </div>
    </Carousel>
  </div>
}
export default App;
