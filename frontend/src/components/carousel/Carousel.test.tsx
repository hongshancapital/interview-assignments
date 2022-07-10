import { render, screen } from '@testing-library/react';
import * as React from 'react';

import Carousel from './Carousel';

const text1 = 'Carousel 1';
const text2 = 'Carousel 2';

const TestCarousel = () => {
  const items = [
    {
      tit: text1,
    },
    {
      tit: text2,
    },
  ];
  return (
    <Carousel>
      {items.map(item => (
        <div>{item.tit}</div>
      ))}
    </Carousel>
  );
};

test('轮播图基本显示', async () => {
  await render(<TestCarousel />);
  expect(screen.getByText(text1)).toBeInTheDocument();
  expect(screen.getByText(text2)).toBeInTheDocument();
});
