import { render } from '@testing-library/react';
import App from './App';

test('should have 3 item', () => {
  const { container } = render(<App />);
  const elements = container.querySelectorAll('.carousel-slide')
  expect(elements.length).toEqual(3)
});
