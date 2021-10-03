import React from 'react';
import './App.css';

import Carousel from './components/carousel';

import xphone  from './assets/iphone.png';
import tablet  from './assets/tablet.png';
import airpods from './assets/airpods.png';

const App: React.FC = () => {
  return (
    <div className="App">
      <Carousel
        duration={2000}
        data={
          [
            {
              title: 'xPhone',
              desc: (
                <>
                  Lots to love. Less to spend.<br />
                  Starting at $399.
                </>
              ),
              style: {
                backgroundImage: `url("${xphone}")`,
                color: '#fff'
              }
            },
            {
              title: 'Tablet',
              desc: (
                <>
                  Just the right amount of everything.
                </>
              ),
              style: {
                backgroundImage: `url("${tablet}")`,
                color: '#000'
              }
            },
            {
              title: (
                <>
                  Buy a Tablet or xPhone for college.<br />
                  Get arPods
                </>
              ),
              style: {
                backgroundImage: `url("${airpods}")`,
                color: '#000'
              }
            }
          ]
        }
      />
    </div>
  );
}

export default App;
