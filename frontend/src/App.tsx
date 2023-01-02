import './App.css';
import { Carousel } from './components/carousel';
import { CustomItem } from './components/custom-item';

interface Item {
  titles: string[],
  descs: string[],
  img_url: string,
  style: {
    background: string
    color: string
  }
}

const data: Item[] = [
  {
    titles: [
      'xPhone'
    ],
    descs: [
      'Lots to love. Less to spend.',
      'Starting at $399.'
    ],
    img_url: require('./assets/iphone.png'),
    style: {
      background: '#000',
      color: '#fff'
    }
  },
  {
    titles: [
      'Tablet'
    ],
    descs: [
      'Just the right amount of everything.',
    ],
    img_url: require('./assets/tablet.png'),
    style: {
      background: '#fff',
      color: '#000'
    }
  },
  {
    titles: [
      'Buy a Tablet or xPhone for college.',
      'Get arPods.'
    ],
    descs: [],
    img_url: require('./assets/airpods.png'),
    style: {
      background: 'rgb(242, 242, 244)',
      color: '#000'
    }
  }
]

function App() {
  return <div className='App'>
    <Carousel>
      {
        data.map(item => <CustomItem item={item} key={item.img_url}></CustomItem>)
      }
    </Carousel>
  </div>;
}

export default App;
