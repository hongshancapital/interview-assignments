import './App.css';
import { Carousel } from './components/Carousel';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

const dataSource = [
  {
    id: 0,
    image: iphone,
    title: 'xPhone',
    textColor: '#fff',
    description: 'Lots to love. Less to spend. <br/> Starting at $399.'
  },
  {
    id: 1,
    image: tablet,
    title: 'Tablet',
    description: 'Just the right amount of everything.'
  },
  {
    id: 2,
    image: airpods,
    title: 'Buy a Tablet or Phone for college. <br/> Get arPods.',
  }
];

function App() {
  return (
    <div className='App'>
      <Carousel dataSource={dataSource} duration={3000} defaultActive={1} autoplay />
    </div>
  );
}

export default App;
