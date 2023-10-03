import { render, screen, fireEvent } from '@testing-library/react';
import Carousel from './CarouselWrapper';

const mockChildren = [
  <li key={1}>Slide 1</li>,
  <li key={2}>Slide 2</li>,
  <li key={3}>Slide 3</li>,
];

describe('Carousel', () => {
  test('renders without crashing', () => {
    render(<Carousel>{mockChildren}</Carousel>);
    expect(screen.getByText('Slide 1')).toBeInTheDocument();
  });


  test('applies the correct HTML classes', () => {
    const { asFragment } = render(<Carousel>{mockChildren}</Carousel>);
    expect(asFragment()).toMatchSnapshot()
  });
});
