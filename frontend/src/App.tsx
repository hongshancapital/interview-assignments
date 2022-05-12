import { useState } from 'react';
import type { Frame } from './type';
import phone from './assets/iphone.png';
import airpods from './assets/airpods.png';
import tablet from './assets/tablet.png';
import { Carousel } from './components/Carousel';
import './App.css';

const DEFAULT_FRAMES: Frame[] = [
  {
    title: 'xPhone',
    description: 'Lots to love. Less to spend. Starting at $399.',
    post: phone,
    backgroundColor: '#111111',
    fontColor: '#fff',
  },
  {
    title: 'Tablet',
    description: 'Juest the right amount of everything.',
    post: tablet,
    backgroundColor: '#FAFAFA',
    fontColor: '#000',
  },
  {
    title: 'Buy a Tablet or xPhone for college. Get arPods.',
    post: airpods,
    backgroundColor: '#F1F1F3',
    fontColor: '#000',
  },
];

function App() {
  const [frames] = useState(DEFAULT_FRAMES);
  return (
    <div className="App">
      <Carousel frames={frames} />
    </div>
  );
}

export default App;
