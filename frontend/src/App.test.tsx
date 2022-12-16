import { render, screen } from '@testing-library/react';
import App from './App';

test('renders learn react link', async () => {
  render(<App />);
  const carousel = await screen.queryByTestId('carousel');
  expect(carousel).toBeInTheDocument();
});
