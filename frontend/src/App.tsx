import Carousel from './components/Carousel'
import airpods from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import './App.css'

const items = [
  {
    title: 'xPhone',
    text: <><div>Lots of love. Less to Spend.</div><div>Starting at $399</div></>,
    imgSrc: iphone,
    style: { color: '#fff' }
  },
  {
    title: 'Tablet',
    text: 'Just the right amount of everything',
    imgSrc: tablet,
  },
  {
    title: <><div>Buy a Tablet or Xphone for college.</div><div>Get airPods.</div></>,
    text: '',
    imgSrc: airpods,
  },
]

function App() {
  return (
    <div className='App'>
      <Carousel
        items={items}
        duration={3}
      />
    </div>
  )
}

export default App;
