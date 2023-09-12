import './App.css';
import Carousel from './components/Carousel/Carousel';

const items = [
  {
    bg: '#111',
    title: ['xPhone'],
    titleClass: 'color-white',
    text: ['Lots to love. Less to spend.', 'Starting at $399.'],
    textClass: 'color-white',
    image: require('./assets/iphone.png')
  },
  {
    bg: '#f9f9f9',
    title: ['Tablet'],
    titleClass: 'color-black',
    text: ['Just the right amount of everything.'],
    ttextClass: 'color-black',
    image: require('./assets/tablet.png')
  },
  {
    bg: '#f0f0f2',
    title: ['Buy a Tablet or xPhone for college.', 'Get airPods.'],
    titleClass: 'color-black',
    text: [],
    textClass: 'color-black',
    image: require('./assets/airpods.png')
  },
]

function App() {
  return <div className='App'>
    <Carousel>
      {items.map(item => (
        <Carousel.Item bg={item.bg} key={item.bg}>
          <div className='content-container'>
            <div className='content'>
              {item.title.map(t => (
                <p className={`title ${item.titleClass}`} key={t}>{t}</p>
              ))}
              {item.text.map(t => (
                <p className={`text ${item.textClass}`} key={t}>{t}</p>
              ))}
            </div>
          </div>
          <div className='img-wrap'>
            <img src={item.image} alt='' />
          </div>
        </Carousel.Item>
      ))}
    </Carousel>
  </div>;
}

export default App;
