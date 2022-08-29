import React from 'react';
import { render } from '@testing-library/react';
import Page from 'src/components/page';
import Carsousel from 'src/components/carousel';
import { CarouselList, animationProps } from 'src/constants';

describe('carousel test', () => {
  test('appear', () => {
    const { getByTestId } = render(<Carsousel {...animationProps}>
      {
        CarouselList.map((carousel,i) => <Page key={i} {...carousel} />)
      }
    </Carsousel>);
    const carouselDOM = getByTestId('carousel-inner');
    expect(carouselDOM).toBeInTheDocument();
  })

  test('childNodes length', () => {
    const { getByTestId } = render(<Carsousel {...animationProps}>
      {
        CarouselList.map((carousel,i) => <Page key={i} {...carousel} />)
      }
    </Carsousel>);
    const carouselDOM = getByTestId('carousel-inner');
    expect(carouselDOM.childNodes.length).toBe(CarouselList.length);
  })
});
