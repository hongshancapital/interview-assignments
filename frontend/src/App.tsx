import './App.css'
import Carousel from './Carousel/index'
import iphoneImg from './assets/iphone.png'
import tabletImg from './assets/tablet.png'
import airpodsImg from './assets/airpods.png'
function App() {
  const swiperList = [
    {
      title: 'xPhone',
      text: 'Lots to spend.\nStarting at $399.',
      img: iphoneImg,
    },
    {
      title: 'Buy a Tablet or xPhone for college.\nGet ar Pods.',
      img: airpodsImg,
    },
    {
      title: 'Tablet',
      text: 'Just the right amount of everything.',
      img: tabletImg,
    },
  ]
  return (
    <div className="App">
      <Carousel swiperList={swiperList} />
    </div>
  )
}

export default App
