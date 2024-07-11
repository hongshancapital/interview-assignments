import Carousel from './components/Carousel';
import img1 from './assets/iphone.png';
import img2 from './assets/tablet.png';
import img3 from './assets/airpods.png';
import './App.css';

function App() {
  // 轮播图传入数据
  const data = [
    {
      image: img1,
      text: (
        <>
          <div className='carousel-item-title'>xPhone</div>
          <div className='carousel-item-desc' style={{ fontWeight: 'normal' }}>Lots to love.Less to spend.<br/>Starting at $399.</div>
        </>
      )  
    },
    {
      image: img2,
      text: (
        <>
          <div className='carousel-item-title' style={{ color:'#000' }}>Tablet</div>
          <div className='carousel-item-desc' style={{ color:'#000', fontWeight: 'normal'}}>Just the right amount of everything.</div>
        </>
      )
    },
    {
      image: img3,
      text: (
        <>
          <div className='carousel-item-title' style={{ color:'#000' }}>Buy a Tablet or xPhone for college.<br/>Get arPods.</div>
        </>
      )
    }
  ];
  return (
      <div className='App'>
        <Carousel
          data={data}
          interval={2}
        />
    </div>
  );
}

export default App;