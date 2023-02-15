import { render } from '@testing-library/react';
import CarouselCard from '../CarouselCard';

describe('CarouselCard', () => {
  it('should render CarouselCard component', () => {
    const { asFragment } = render(
      <CarouselCard
        title="title"
        desc="desc"
        color="#fff"
        bgColor="#fff"
        bgImage="image url"
      />
    );
    expect(asFragment()).toMatchSnapshot();
  });
});
