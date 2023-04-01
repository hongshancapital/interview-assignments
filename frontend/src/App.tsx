import './App.css'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airpods from './assets/airpods.png'
import Carousel from './carousel'

function App() {
  const items = []
  items.push(
    <div className='frame bg-black text-white'>
      <span className='title'>xPhone</span>
      <span className='desc'>Lots to love. Less to spend. <br/> Starting at $399.</span>
      <img className='pic' src={iphone} alt='' />
    </div>
  )
  items.push(
    <div className='frame bg-white text-black'>
      <span className='title'>Tablet</span>
      <span className='desc'>Just the right amout of everything. </span>
      <img className='pic' src={tablet} alt='' />
    </div>
  )
  items.push(
    <div className='frame bg-grey text-black'>
      <span className='title'>Buy a Tablet or xPhone for college. <br/> Get airPods.</span>
      <span className='desc'></span>
      <img className='pic' src={airpods} alt='' />
    </div>
  )

  return <div className='App'>
    <div className='demo'>
      <Carousel stayTime={2500} animationTime={500} items={items}></Carousel>
    </div>
  </div>;
}

export default App;
