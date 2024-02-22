import './App.css';
import Carousel from './components/Carousel';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

function App() {
  return <div className='App'>
    {/* write your component here */}
    <Carousel autoplay={3000} speed={1000} className='carousel'>
      <div className='carousel-item' style={{ color: '#fff', backgroundColor: '#111' }}>
        <div className='carousel-img'>
          <img src={iphone} alt='' style={{ width: '596%', position: 'relative', left: '-750px', bottom: '700px' }} />
        </div>
        <div className='tips'>
          <h1 className='title'>xPhone</h1>
          <div className='text'>
            Lots to love.Less to spend.
          </div>
          <div className='text'>
            Starting at $399.
          </div>
        </div>
      </div>
      <div className='carousel-item' style={{ backgroundColor: 'rgb(250 250 250)' }}>
        <div className='carousel-img'>
          <img src={tablet} alt='' style={{ width: '951%', position: 'relative', left: '-1282px', bottom: '497px' }} />
        </div>
        <div className='tips'>
          <h1 className='title'>Tablet</h1>
          <div className='text'>
            Just the right amount of everything
          </div>
        </div>
      </div>
      <div className='carousel-item' style={{ backgroundColor: 'rgb(241 241 243)' }}>
        <div className='carousel-img'>
          <img src={airpods} alt='' style={{ width: '1460%', position: 'relative', left: '-2046px', bottom: '512px' }} />
        </div>
        <div className='tips'>
          <h1 className='title'>Buy a Tablet or xPhone for college.</h1>
          <div className='text' style={{ fontSize: '56px' }}>Get arPods.</div>
        </div>
      </div>
    </Carousel>
  </div>;
}

export default App;
