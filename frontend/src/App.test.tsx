import { render } from '@testing-library/react';
import App from './App';

test('成功渲染carousel', () => {
  const { getByTestId } = render(<App />);
  const linkElement = getByTestId('carousel');
  expect(linkElement).toBeInTheDocument();
});
