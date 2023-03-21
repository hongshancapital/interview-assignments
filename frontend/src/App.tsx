import './App.css';
import { Carousel } from './components/Carousel';
import { CarouselItemProps } from './components/CarouselItem';
import { LIGHT_GRAY_COLOR, GRAY_COLOR, LIGHT_BLACK_COLOR, WHITE_COLOR } from './constants/color';

const AIRPODS_SRC = require('./assets/airpods.png')
const IPHONE_SRC = require('./assets/iphone.png')
const TABLET_SRC = require('./assets/tablet.png')

const CAROUSEL_DATA: CarouselItemProps[] = [
  {key: 'xPhone', title: ['xPhone'], description: ['Lots to love. Less to spend.', 'Starting at $399.'], backgroundImage: IPHONE_SRC, fontColor: WHITE_COLOR, backgroundColor: LIGHT_BLACK_COLOR,},
  {key: 'Table', title: ['Tablet'], description: ['Just the right amount of everything.'], backgroundImage: TABLET_SRC, backgroundColor: LIGHT_GRAY_COLOR},
  {key: 'Airpods', title: ['Buy a Tablet or xPhone for college.', 'Get airPods.'], backgroundImage: AIRPODS_SRC, backgroundColor: GRAY_COLOR}
]

function App() {
  return <div className='App'><Carousel carouselData={CAROUSEL_DATA} /></div>;
}

export default App;
