import './App.css';
import './carousel/carousel.css'
import { AutoCarouselWithProgressbars } from './carousel';

function App() {
  return (
    <div className='App'>
      <AutoCarouselWithProgressbars timeout={3000}>
        <img alt='demo pic' src='/demo_1.JPG' />
        <img alt='demo pic 2' src='/demo_2.JPG' />
        <img alt='demo pic 3' src='/demo_3.JPG' />
      </AutoCarouselWithProgressbars>
    </div>
  )
}

export default App;
