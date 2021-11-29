import React from "react";
import { Carousel } from './component/Carousel';
import { CarouselItemProps } from './component/Carousel/item';
import "./App.css";

function App() {
  const carouselItems: Array<CarouselItemProps> = [
    {
      id: 'iphone',
      title: ['xPhone'],
      content: ['Lots to love. Less to spend.', 'Starting at $399'],
      image: require('./assets/iphone.png').default,
      color: '#fff',
      bgColor: '#111'
    },
    {
      id: 'tablet',
      title: ['Tablet'],
      content: ['Just the right amount of everything'],
      image: require('./assets/tablet.png').default,
      bgColor: '#fafafa'
    },
    {
      id: 'airpods',
      title: ['Buy a Tablet or xPhone for college.', 'Get airPods'],
      image: require('./assets/airpods.png').default,
      bgColor: '#f1f1f3'
    }
  ];

  return (
    <div className="App">
      <Carousel
        interval={3000}
        items={carouselItems}
      />
    </div>
  );
}
export default App;
