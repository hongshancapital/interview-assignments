import React, { useState } from 'react';
import './App.css';
import Carousel from './components/Carousel';
import Slide from './models/slide';

function App() {
  // eslint-disable-next-line
  const [slideList, setSlideList] = useState<Slide[]>([
    {
      id: '1',
      title: 'xPhone',
      text: 'Lots to love. Less to spend.\nStarting at $399.',
      imageUrl: './images/iphone-x.png'
    },
    {
      id: '2',
      title: 'Tablet',
      text: 'Just the right amount of everything.',
      imageUrl: './images/tablet-x.png'
    },
    {
      id: '3',
      title: 'Buy a Tablet or xPhone for college.\nGet arPods.',
      text: '',
      imageUrl: './images/airpods-x.png'
    }
  ]);

  return (
    <div className="App">
      <Carousel slides={slideList} pauseTime={2.52} transTime={0.48} />
    </div>
  );
}

export default App;
