import { render, screen } from '@testing-library/react';
import Carousel from './Carousel';

const slides = [
  { imgUrl: 'image1.jpg', text: 'Slide 1' },
  { imgUrl: 'image2.jpg', text: 'Slide 2|Subtitle 2' },
  { imgUrl: 'image3.jpg', text: 'Slide 3|Subtitle 3|Description 3' },
];

describe('Carousel', () => {
  test('renders slides correctly', () => {
    render(<Carousel slides={slides} />);
    const slide1 = screen.getByAltText('Slide 0');
    const slide2 = screen.getByAltText('Slide 1');
    const slide3 = screen.getByAltText('Slide 2');
    expect(slide1).toBeInTheDocument();
    expect(slide2).toBeInTheDocument();
    expect(slide3).toBeInTheDocument();
  });

  test('renders slide text correctly', () => {
    render(<Carousel slides={slides} />);
    const slideText1 = screen.getByText('Slide 1');
    const slideText2 = screen.getByText('Slide 2');
    const slideText3 = screen.getByText('Slide 3');
    expect(slideText1).toBeInTheDocument();
    expect(slideText2).toBeInTheDocument();
    expect(slideText3).toBeInTheDocument();
    expect(screen.getByText('Subtitle 2')).toBeInTheDocument();
    expect(screen.getByText('Subtitle 3')).toBeInTheDocument();
    expect(screen.getByText('Description 3')).toBeInTheDocument();
  });
});