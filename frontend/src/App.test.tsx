import { render } from '@testing-library/react';
import App from './App';

test('renders carousel', () => {
  const { getByText } = render(<App />);
  const carouselCardElement = getByText(/Just the right amount of everything./i);
  expect(carouselCardElement).toBeInTheDocument();
});
