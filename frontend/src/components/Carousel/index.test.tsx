import React from 'react';
import { render, act } from '@testing-library/react';
import Carousel from './index';
import mockCarouselData from '../../mock/carousel';

const duration = 3000;

describe('Render Component <Carousel>', () => {
  test('render content', () => {
    const { container } = render(<Carousel data={mockCarouselData} time={duration} />);

    const expectText = mockCarouselData.map((item)=>{
      return item.title.join('') + (item.subTitle??[]).join('')
    }).join('')

    expect(container.textContent).toBe(expectText);
  });
  
  test('switch automatic', () => {
    jest.useFakeTimers();

    const { container } = render(<Carousel data={mockCarouselData} time={duration} />);

    const secondDots = container.querySelectorAll('.dots-wrap>.dots-item')[1];
    const secondScreen = container.querySelectorAll('.carousel-item')[1];

    expect(secondDots).not.toHaveClass('active-dots');
    expect(secondScreen).not.toHaveClass('screen-0');
    
    act(() => {
      jest.runOnlyPendingTimers();
    });
    
    expect(secondDots).toHaveClass('active-dots');
    expect(secondScreen).toHaveClass('screen-0');
  });

  test('switch manual', async () => {
    const { container } = render(<Carousel data={mockCarouselData} time={duration} />);

    const lastDots = container.querySelectorAll('.dots-wrap>.dots-item')[mockCarouselData.length - 1];
    const lastScreen = container.querySelectorAll('.carousel-item')[mockCarouselData.length - 1];

    expect(lastDots).not.toHaveClass('active-dots');
    expect(lastScreen).not.toHaveClass('screen-0');

    act(() => {
      lastDots && lastDots.dispatchEvent(new MouseEvent('click', {bubbles: true}));
    });

    expect(lastDots).toHaveClass('active-dots');
    expect(lastScreen).toHaveClass('screen-0');
  });
});