import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

describe('test App', () => {
  test('check render', () => {
    const { container } = render(<App />);
    const Apps = container.querySelectorAll('.App');
    expect(Apps.length).toBe(1);

    const Carousels = container.querySelectorAll('.carousel_panel');
    expect(Carousels.length).toBe(1);

    const CarouselItems = container.querySelectorAll('.my_carousel_item');
    expect(CarouselItems.length).toBe(3);
  });
});
