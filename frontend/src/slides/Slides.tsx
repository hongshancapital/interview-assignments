import React from 'react';
import { Caroussel } from '../components/Carousel';

export const Slides = () => {
  const items = [
    {
      title: 'test-1',
      bgImage: '',
      bgColor: 'red', 
    },
    {
      title: 'test-2',
      bgImage: '',
      bgColor: 'blue', 
    },
    {
      title: 'test-3',
      bgImage: '',
      bgColor: 'green', 
    },
    {
      title: 'test-4',
      bgImage: '',
      bgColor: 'yellow', 
    },
  ];
  const getItems = () => {
    return items.map(({title, bgColor, bgImage}) => {
      const style = {
        backgroundColor: bgColor,
        backgroundImage: bgImage,
      };
      return (<div className="w-full h-full flex items-center" style={style}>
        <h3 className="w-full text-center leading-normal">{title}</h3>
      </div>)
    })
  }
  return (<Caroussel
    items={getItems()}
    interval={1500}
    keyboard
    indicators
    pasued="hover"
    cycle
    control
  />);
}