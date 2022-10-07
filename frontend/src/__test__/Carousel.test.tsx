import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Carousel from '../components/Carousel';

describe('Carousel test', () => {
  it('Carousel rendered', () => {
    const { container } = render(<Carousel />);
    expect(container).toMatchSnapshot();
  });

  it('empty Carousel', () => {
    const { getByTestId } = render(<Carousel />);
    const carouselInner = getByTestId('carousel-inner-wrapper');
    const indicator = getByTestId('indicator-wrapper');
    expect(carouselInner).toBeInTheDocument();
    expect(carouselInner).toBeEmptyDOMElement();
    expect(indicator).toBeInTheDocument();
    expect(indicator).toBeEmptyDOMElement();
  });

  it('Carousel filled with text', () => {
    const { getByTestId } = render(<Carousel items={['text1', 'text2']} />);
    const carouselInner = getByTestId('carousel-inner-wrapper');
    const indicator = getByTestId('indicator-wrapper');
    expect(carouselInner).toBeInTheDocument();
    expect(carouselInner.childElementCount).toEqual(2);
    expect(carouselInner.firstChild?.textContent).toEqual('text1');
    expect(carouselInner.lastChild?.textContent).toEqual('text2');
    expect(indicator.childElementCount).toEqual(2);
  });

  it('click Indicator of Carousel', async () => {
    const { getByTestId } = render(<Carousel items={['text1', 'text2']} />);
    const carouselInner = getByTestId('carousel-inner-wrapper');
    const indicator = getByTestId('indicator-wrapper');
    fireEvent.click(indicator.lastChild!);
    expect(carouselInner).toHaveStyle({ transform: 'translateX(-100%)' });
  });

  it('wait Carousel play', async () => {
    const { getByTestId } = render(<Carousel items={['text1', 'text2']} periodicTime={1000} />);
    const carouselInner = getByTestId('carousel-inner-wrapper');
    await new Promise(resolve => setTimeout(() => resolve(''), 1200));
    expect(carouselInner).toHaveStyle({ transform: 'translateX(-100%)' });
  });
});
