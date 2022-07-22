import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Carousel from '.';

import xphone from "./assets/iphone.png";
import tablet from "./assets/tablet.png";

test('render empty Carousel', () => {
    const { queryByText } = render(<Carousel config={[]}/>);
    expect(queryByText(/Carousel/i)).toBeNull()
});

const config = [
    {
      key: 0,
      title: ['xPhone1'],
      subTitle: ['Lots to love.Less to speed.', 'Starting at $399'],
      image: xphone,
      backgroundColor: '#111111',
      color: '#fff',
    },
    {
        key: 1,
        title: ['Tablet1'],
        subTitle: ['Just the right amount of everything'],
        image: tablet,
        backgroundColor: '#fafafa',
      },
  ]

test('render Carousel', () => {
    const { getByText, container } = render(<Carousel config={config}/>);
    expect(getByText(/xPhone1/i)).toBeInTheDocument();
    fireEvent.click(container.getElementsByClassName('bar-progress')[1]);
});