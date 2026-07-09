import Carousel from './components/Carousel'
import airpods from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import './App.css'

function App() {
  return (
    <div className='App'>
      <div className='carousel-area'>
        <Carousel>
          <img width='100%' height='100%' src={iphone} alt='' />
          <img width='100%' height='100%' src={tablet} alt='' />
          <img width='100%' height='100%' src={airpods} alt='' />
        </Carousel>
      </div>
    </div>
  )
}

export default App
