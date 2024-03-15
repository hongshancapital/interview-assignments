import { render, screen } from '@testing-library/react';
import { Carousel } from './Carousel';
import { useIndex } from './hooks';

jest
  .mock('./hooks', () => ({
    useIndex: jest.fn(() => 0),
  }))
  .mock('./Indicators', () => ({
    Indicators: () => <div>Indicators</div>,
  }));

const dummyChildren = [
  <div key="1">1</div>,
  <div key="2">2</div>,
  <div key="3">3</div>,
];

describe('Carousel', () => {
  it('renders children', () => {
    render(<Carousel>{dummyChildren}</Carousel>);

    expect(screen.getAllByLabelText('carousel item')).toHaveLength(3);
  });

  it('renders Indicators', () => {
    render(<Carousel>{[]}</Carousel>);

    expect(screen.getByText('Indicators')).toBeInTheDocument();
  });

  it('renders children with correct style when activeIndex is 1', () => {
    jest.mocked(useIndex).mockReturnValueOnce(1);

    render(<Carousel>{dummyChildren}</Carousel>);

    const carouselItems = screen.getByLabelText('carousel items');

    expect(carouselItems).toHaveStyle({
      width: '300%',
      transform: 'translateX(calc(-1 * 100% / 3 * 1))',
    });
  });

  it('sets aria-hidden to true for all children except activeIndex', () => {
    jest.mocked(useIndex).mockReturnValueOnce(1);

    render(<Carousel>{dummyChildren}</Carousel>);

    const carouselItems = screen.getAllByLabelText('carousel item');

    expect(carouselItems[0]).toHaveAttribute('aria-hidden', 'true');
    expect(carouselItems[1]).toHaveAttribute('aria-hidden', 'false');
    expect(carouselItems[2]).toHaveAttribute('aria-hidden', 'true');
  });
});
