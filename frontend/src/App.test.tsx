import { render } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  const { getByText } = render(<App />);
  const linkElement = getByText(/buy a table or xphone for college\. get arpods/i)
  expect(linkElement).toBeInTheDocument();
});
