import { render, screen } from '@testing-library/react';
import { Carousel } from './Carousel';
import { DEFAULT_CAROUSEL_DURATION } from './constants';
import { act } from 'react-dom/test-utils';

describe('Carousel', () => {
  jest.useFakeTimers();
  it('renders correctly', () => {
    const { asFragment } = render(<Carousel />);
    expect(asFragment()).toMatchSnapshot();
  });

  it('sets the correct transform for active item for default props', () => {
    render(<Carousel />);
    act(() => {
      jest.advanceTimersByTime(DEFAULT_CAROUSEL_DURATION);
    });
    expect(screen.getByLabelText('carousel items')).toHaveStyle({
      width: '300vw',
      transform: 'translateX(-100vw)',
    });
  });
});
