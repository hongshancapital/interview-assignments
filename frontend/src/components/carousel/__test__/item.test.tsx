import { CarouselItems } from '../items';
import mountTest from '../../../tests/mountTest';
import { render } from '@testing-library/react';

describe('CustomItem', () => {
  mountTest(CarouselItems);

  it('should render correctly', () => {
    const { container } = render(
      <CarouselItems >
        <div>test</div>
        <div>test</div>
        <div>test</div>
        <div>test</div>
      </CarouselItems>
    );
    expect(container).toBeInTheDocument();
    expect(container.querySelector('.list-item')).toHaveStyle(`width: 25%`);
  });
});