import Swiper from './Slider/ui'
import React from 'react';
import './App.css';

function App() {
  const sliderList = [
    {
      id: 1,
      descriptionInfo: {
        title: ['xPhone'],
        description: ['Lots to love.Less to speed', 'Starting at $399'],
      },
      imageInfo: {
        src: require('./assets/iphone.png'),
        alt: 'xPhone',
      },
      style: {
        color: 'rgba(255, 255, 255, 1)',
        backgroundColor: 'rgba(0, 0, 0, 1)',
      }
    },
    {
      id: 2,
      descriptionInfo: {
        title: ['Tablet'],
        description: ['Just the right amount of everything'],
      },
      imageInfo: {
        src: require('./assets/tablet.png'),
        alt: 'Tablet',
      },
      style: {
        color: 'rgba(0, 0, 0, 1)',
        backgroundColor: 'rgba(255, 255, 255, 1)',
      }
    },
    {
      id: 3,
      descriptionInfo: {
        title: ['Buy a Tablet or xPhone for college.', 'Get airPods'],
        description: [],
      },
      imageInfo: {
        src: require('./assets/airpods.png'),
        alt: 'airPods',
      },
      style: {
        color: 'rgba(0, 0, 0, 1)',
        backgroundColor: 'rgba(255, 255, 255, 1)',
      }
    }
  ];
  return (
    <div className="App">
      <Swiper
        sliderList={sliderList}
        dots={true}
        speed={1000}
        delay={3000}
      ></Swiper>
    </div>
  );
}

export default App;
