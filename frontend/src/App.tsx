import './App.css';

import type { CarouselItem } from 'entities/carousel'
import { Carousel } from 'entities/carousel'

import IphoneImg from 'assets/iphone.png'
import TabletImg from 'assets/tablet.png'
import AirpodsImg from 'assets/airpods.png'

const List: CarouselItem[] = [
  {
      id: 1,
      img: IphoneImg,
      title: 'xPhone',
      description: 'Lots to Love. Less to spend. \n Starting at $399.',
      color: '#fff',
      backgroundColor: '#111'
  },
  {
      id: 2,
      img: TabletImg,
      title: 'Tablet',
      description: 'Just the right amount of everything.',
      color: '#000',
      backgroundColor: '#fafafa'
  },
  {
      id: 3,
      img: AirpodsImg,
      title: 'Buy a Tablet or xPhone for college. \n Get airPods',
      description: '',
      color: '#000',
      backgroundColor: '#f1f1f3'
  },
]


function App() {
  return <div className='App'>
    <Carousel list={List} />
  </div>;
}

export default App;
