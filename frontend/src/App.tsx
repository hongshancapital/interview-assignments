import './App.css';
import Carousel from './components/Carousel';
import img1 from './assets/iphone.png'
import img2 from './assets/tablet.png'
import img3 from './assets/airpods.png'

function App() {
  return <div className='App'>
    <Carousel
      data={[
        {
          src: img1, 
          content: <div className='content' style={{ color: '#fff', width: 360 }}>
            <div className='title'>Xphone</div>
            <div className='text'>Lots to love .Less to spend. Starting  at $399.</div>
          </div>
        },
        { src: img2,
          content: <div className='content'>
          <div className='title'>Tablet</div>
          <div className='text'>Just the right amount of everything.</div>
        </div>
        },
        { src: img3,
          content: <div className='content'>
          <div className='title'>Buy a Tablet or iphone for college.</div>
          <div className='title'>Get ar pods.</div>
        </div> },
      ]}
    />
  </div>;
}

export default App;
