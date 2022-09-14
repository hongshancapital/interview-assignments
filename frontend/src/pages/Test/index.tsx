import React from 'react';
import Carousel from '../../components/Carousel';
import TestItem from './TestItem';
import img1 from '../../assets/iphone.png';
import img2 from '../../assets/tablet.png';
import img3 from '../../assets/airpods.png';

const testData = [
  {
    title: 'xPhone',
    describes: ["Lots to love. Less to spend.", "Starting at $399"],
    textColor: 'white',
    img: img1
  },
  {
    title: 'Tablet',
    describes: ['Just the right amount of everything'],
    img: img2
  },
  {
    title: 'Buy a Tablet or xPhone for college. Get airPods',
    img: img3
  },
];

const Test = () => {
  return (
    <Carousel
      periodicTime={3000}
      items={testData.map(data => <TestItem {...data} />)}
    />
  );
};

export default Test;
