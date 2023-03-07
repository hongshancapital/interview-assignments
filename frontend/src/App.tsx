import './App.css';

import Carousel from './components/Carousel';

import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';

const list = [
  {
    title: 'xPhone',
    text: 'Lots to love. Less to spend.\nStarting at $399.',
    img: iphone,
  },
  {
    title: 'Tablet',
    text: 'Just the right amount of everything.',
    img: tablet,
  },
  {
    title: 'Buy a Tablet or Phone for college.\nGet arPods.',
    img: airpods,
  },
];

function App() {
  return (
    <div className='App'>
      <Carousel
        switchDuration={0.5}
        previewDuration={2}
        list={list}
      />
    </div>
  );
}

export default App;
