import { act, render, screen } from '@testing-library/react';
import App from './App';

describe('App', () => {
  beforeAll(() => {
    jest.useFakeTimers();
  });

  describe('Carousel', () => {
    it('renders Carousel with CarouselItems', () => {
      const { container } = render(<App />);

      expect(container).toMatchSnapshot();
    });

    it('displays correct index of CarouselItem and sets correct indicator style after default duration', () => {
      render(<App />);

      expect(
        screen.queryByRole('heading', { name: 'Tablet' })
      ).not.toBeInTheDocument();
      expect(screen.getAllByLabelText('indicator progress')[1]).toHaveStyle({
        opacity: '0',
        animation: 'none',
      });

      act(() => {
        jest.advanceTimersByTime(3000);
      });

      expect(screen.getByRole('heading', { name: 'Tablet' })).toBeVisible();
      expect(screen.getAllByLabelText('indicator progress')[1]).toHaveStyle({
        opacity: '1',
        animation: 'progress 3000ms linear',
      });
    });
  });
});
