import './App.css';
import Carousel from './carousel';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

const images = [
  airpods,
  iphone,
  tablet,
];

const captions = [
  'First image',
  'Second image',
  'Third image'
];

const App: React.FC = () => {
  return (
    <div>
      <Carousel images={images} captions={captions} interval={3000} />
    </div>
  );
};

export default App;

