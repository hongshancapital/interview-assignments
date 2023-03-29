import { render, screen } from '@testing-library/react';
import { CarouselItem } from './CarouselItem';

describe('CarouselItem', () => {
  it('renders correctly', () => {
    const { asFragment } = render(
      <CarouselItem
        imgSrc="testImage"
        title="test title"
        subTitle="test subtitle"
        className="testClass"
      />
    );

    expect(asFragment()).toMatchSnapshot();
  });

  it('does not render optional props if not provided', () => {
    render(<CarouselItem imgSrc="testImage" title="test title" />);

    expect(screen.getByRole('heading', { level: 3 })).toBeEmptyDOMElement();
    expect(screen.getByLabelText('carousel item')).toHaveClass('carouselItem ');
  });
});
