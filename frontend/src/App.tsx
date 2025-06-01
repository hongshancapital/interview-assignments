import './App.css';
import Carousel from './components/Carousel';
import { CarouselData } from './components/Carousel/interface';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

function App() {

  const dataSource: CarouselData[] = [
    { title: 'xPhone', content: 'Lots to love.Less to spend.Starting at $399', imgUrl: iphone, style: { color: '#fff', backgroundColor: '#111' } },
    { title: 'Tablet', content: 'Just the right amount of everything', imgUrl: tablet, style: { backgroundColor: 'rgb(250 250 250)' } },
    { title: 'Buy a Tablet or xPhone for college.Get arPods.', content: '', imgUrl: airpods, style: { backgroundColor: 'rgb(241 241 243)' } },
  ];

  return <div className='App'>
    {/* write your component here */}
    <Carousel dataSource={dataSource} showSwitch autoPlay />
  </div>;
}

export default App;
