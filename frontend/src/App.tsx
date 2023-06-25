import Carousel from './components/Carousel';
import Card, { CardProps } from './components/Card';
import imgAirpods from './assets/airpods.png'
import imgiPhone from './assets/iphone.png';
import imgTablet from './assets/tablet.png';
import './App.css';

const cardList: CardProps[] = [{
  imgUrl: imgiPhone,
  title: 'xPhone',
  mode: 'light',
  descriptions: [
    'Lots to Love. Less to spend.',
    'Starting at $399.'
  ],
}, {
  imgUrl: imgTablet,
  title: 'Tablet',
  descriptions: [
    'Just the right amount of everything.'
  ],
}, {
  imgUrl: imgAirpods,
  title: 'Buy a Tablet or xPhone for colledge.',
  descriptions: [
    'Get airPods.'
  ],
}];

function App() {
  return (
    <div className='app'>
      <Carousel className='app__carousel'>
        {cardList.map(item => <Card key={item.imgUrl} {...item}/>)}
      </Carousel>
    </div>
  );
}

export default App;
