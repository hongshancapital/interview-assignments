import React from 'react';
import { render } from '@testing-library/react';
import fireEvent from '@testing-library/user-event';

import { CarouselIndicator } from './indicator';

describe('Test CarouselIndicator', () => {
  const item = {
    auto: false,
    interval: 5000,
    isActive: false,
  };
  test('check render', () => {
    const { container } = render(<CarouselIndicator {...item} />);
    const containerElements = container.querySelectorAll('.carousel_indicator');
    expect(containerElements.length).toBe(1);

    const processElements = container.querySelectorAll(
      '.carousel_indicator__process'
    );
    expect(processElements.length).toBe(1);
  });

  test('check active', () => {
    const { container } = render(<CarouselIndicator {...item} isActive auto />);
    const processElements = container.querySelectorAll(
      '.carousel_indicator__active'
    );
    expect(processElements.length).toBe(1);
  });

  test('check click', () => {
    const onClick = jest.fn();
    const { container } = render(
      <CarouselIndicator {...item} onClick={onClick} />
    );
    const [containerElement] = container.querySelectorAll(
      '.carousel_indicator'
    );
    fireEvent.click(containerElement);
  });
});
