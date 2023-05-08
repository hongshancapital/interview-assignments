import { render } from '@testing-library/react';
import App from './App';

test('should have 3 Pannel', () => {
  const { container } = render(<App />);
  const pannelElements = container.querySelectorAll('.pannel-wrapper');
  expect(pannelElements.length).toBe(3);
});
