import './App.css';
import Carousel from './component/carousel';
import { CarouselProps } from './component/carousel';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

const testData: CarouselProps = {
  data: [
    {
      titles: ['xPhone'],
      desc: ['Lots to love. Less to spend.', 'Starting at $399.'],
      imgUrl: iphone,
      backgroundColor: '#111',
      color: '#fff',
    },
    {
      titles: ['Tablet'],
      desc: ['Just the right amount of everything.'],
      imgUrl: tablet,
      backgroundColor: '#fafafa',
      color: '#000',
    },
    {
      titles: ['Buy a Tablet or xPhone for college.', ' Get arPods.'],
      imgUrl: airpods,
      backgroundColor: '#f1f1f3',
      color: '#000',
    },
  ]
}

function App() {
  return (
    <div className='App'>
      <Carousel {...testData}/>
    </div>
  );
}

export default App;
