import './App.css'
import iphone from '@/assets/iphone.png'
import tablet from '@/assets/tablet.png'
import airpods from '@/assets/airpods.png'
import Carousel from './components/Carousel'

function App() {
  const carouselList: { [k: string]: string }[] = [
    {
      title: 'xPhone',
      description: 'Lots to love.less to spend.\nStarting at $399.',
      // dark: true,
      img: iphone
    },
    {
      title: 'Tablet',
      description: 'Just the right amount of exerything.',
      img: tablet
    },
    {
      title: 'Buy a Tablet or xPhone for college.\nGet arPods.',
      description: '',
      img: airpods
    }
  ]
  return (
    <div className="App">
      <Carousel list={carouselList} showIndicatorBar />
    </div>
  )
}

export default App
