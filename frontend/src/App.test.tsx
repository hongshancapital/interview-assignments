import { render } from '@testing-library/react';
import App from './App';
import { act } from 'react-dom/test-utils';

test('renders learn react link', async () => {
  jest.useFakeTimers();

  const { getByText, getByTestId, getAllByTestId } = render(<App />);
  expect(getByText('xPhone')).toBeInTheDocument();
  const sliderProgressArr = getAllByTestId('carousel-slider-progress');
  expect(sliderProgressArr).toHaveLength(3);

  const scrollContianer = getByTestId('carousel-scroll-container');

  expect(scrollContianer.style.transform).toBe('translateX(0px)');
  act(() => {
    jest.advanceTimersByTime(4000);
  });

  expect(sliderProgressArr[0]).toHaveStyle({ width: '0px' });
  expect(sliderProgressArr[1]).toHaveStyle({ width: '40px' });
  expect(sliderProgressArr[2]).toHaveStyle({ width: '0px' });
});
