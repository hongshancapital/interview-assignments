import './App.css';
import { CarouSel } from './components/Carousel';

function App() {
  return (<div className='App'>
    <CarouSel transitionDuration={1} >

      <div className='iphonSlide commonSlide'>
        <div className='title'>xPhone</div>
        <div className='subTitle'>
          Lots to love. Less to spend.<br />
          Starting at $399.
        </div>
      </div>

      <div className='tabletSlide commonSlide'>
        <div className='title'>Tablet</div>
        <div className='subTitle'>Just the right amount of everything.</div>
      </div>

      <div className='airpodsSlide commonSlide'>
        <div className='title'>
          Buy a Tablet or xPhone for college.<br />
          Get arPods.
        </div>
      </div>
      
    </CarouSel>
  </div>)
}

export default App;
