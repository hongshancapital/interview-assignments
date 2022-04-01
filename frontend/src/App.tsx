import React from 'react';
import './App.css';
import Carousel from './components/carousel';

import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import classNames from 'classnames';

const data = [
  {
    title: 'xPhone',
    description: 'Lots to love. Less to spend. Starting at $399',
    image: iphone,
    className: 'xphone',
  },
  {
    title: 'Tablet',
    description: `Just the right amount of everything.`,
    image: tablet,
    className: 'tablet',
  },
  {
    title: 'Buy a Tablet or xPhone for college',
    description: 'Get arPods.',
    image: airpods,
    className: 'airpods',
  },
];

function App() {
  return (
    <div className="App">
      <Carousel duration={10000} autoplay>
        {data.map((d, i) => {
          return (
            <div key={d.title + i} className={classNames('wrapper', d.className)}>
              <div className="text">
                <h2>{d.title}</h2>
                <p>{d.description}</p>
              </div>
              <div className="pic" style={{ backgroundImage: `url(${d.image})` }}></div>
            </div>
          );
        })}
      </Carousel>
    </div>
  );
}

export default App;
