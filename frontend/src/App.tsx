import React from 'react';
import './App.css';
import { Carousel } from './components/Carousel';
import { FullScreen } from './components/FullScreen';

const commonStyle = { fontSize: 32, fontWeight: 'bold', color: '#fff' };
function App() {
  return (
    <div className="App">
      <Carousel>
        <Carousel.Item>
          <FullScreen center style={{ ...commonStyle, backgroundColor: '#32bbeb' }}>
            Hello World
          </FullScreen>
        </Carousel.Item>
        <Carousel.Item>
          <FullScreen center style={{ ...commonStyle, backgroundColor: '#45eebc' }}>
            Thank you for viewing my work
          </FullScreen>
        </Carousel.Item>
        <Carousel.Item>
          <FullScreen center style={{ ...commonStyle, backgroundColor: '#bbddee' }}>
            Destined to meet for thousands of miles
          </FullScreen>
        </Carousel.Item>
        <Carousel.Item>
          <FullScreen center style={{ ...commonStyle, backgroundColor: '#abadee' }}>
            Bye~
          </FullScreen>
        </Carousel.Item>
      </Carousel>
    </div>
  );
}

export default App;
