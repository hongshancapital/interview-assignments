import './App.css';
import { Carousel, CarouselItem } from './components/Carousel'
import iphone from './assets/iphone.png'
import airpods from './assets/airpods.png'
import tablet from './assets/tablet.png'

const listItems = [{
  title: 'xPhone',
  titleColor: '#FFF',
  desc: 'Lots to love. Less to spend.\nStarting at $399',
  descColor: '#FFF',
  image: iphone,
  id: 'xPhone'
}, {
  title: 'Tablet',
  desc: 'Just the right amount of everything.',
  image: tablet,
  id: 'Tablet'
}, {
  title: 'Buy a Tablet or xPhone forr college.\nGet airPods',
  image: airpods,
  id: 'airPods'
}]

function App() {
  return <div className='App'>
    <Carousel
      autoplay
    >{listItems.map(item => (
      <CarouselItem key={item.id}>
        <h1 style={{ color: item.titleColor }} className='carousel-item-title'>{item.title}</h1>
        <p style={{ color: item.descColor }} className='carousel-item-desc'>{item.desc}</p>
        <img src={item.image} alt="" className='carousel-item-img'/>
      </CarouselItem>
    ))}</Carousel>
  </div>;
}

export default App;
