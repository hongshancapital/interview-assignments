import { render } from '@testing-library/react';
import { CarouselItem } from './CarouselItem';

describe('CarouselItem', () => {
  const dummyProps = {
    imgSrc: 'test image',
    title: 'Title',
    subTitle: 'Sub Title',
    className: 'test-class',
  };

  it('renders correctly with given props', () => {
    const { container } = render(<CarouselItem {...dummyProps} />);
    expect(container).toMatchSnapshot();
  });

  it('renders correctly with default props', () => {
    const { container } = render(
      <CarouselItem imgSrc="test image" title="Test title" />
    );
    expect(container).toMatchSnapshot();
  });
});
