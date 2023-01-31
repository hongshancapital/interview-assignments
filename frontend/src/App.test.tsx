import { render } from '@testing-library/react';

import App from './App';

test('App Component', () => {
  const { getByText } = render(<App />)
  const appNode = getByText((content, element) => {
    return /App/.test(element?.className || '')
  })
  expect(appNode).toBeInTheDocument()
});
