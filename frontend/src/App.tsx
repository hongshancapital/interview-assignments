import './App.css';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';
import Carousel, { ICarouselItem } from './components/Carousel';

const data: ICarouselItem[] = [
  {
    img: iphone,
    titles: ['xPhone'],
    descs: ['Lots to love. Less to spend.', 'Starting at $399.'],
    wrapperStyle: {
      color: '#FFFFFF',
      backgroundColor: '#0F0F0F',
    },
  },
  {
    img: tablet,
    titles: ['Tablet'],
    descs: ['Just the right amount of everything.'],
    wrapperStyle: {
      color: '#000000',
      backgroundColor: '#FAFAFA',
    },
  },
  {
    img: airpods,
    titles: ['Buy a Tablet or xPhone for college.', ' Get arPods.'],
    wrapperStyle: {
      color: '#000000',
      backgroundColor: '#F2F2F4',
    },
  },
];

function App() {
  return (
    <div className="App">
      <Carousel data={data} duration={3000} />
    </div>
  );
}

export default App;
