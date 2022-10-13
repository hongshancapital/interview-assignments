import React from 'react';
import { Provider } from 'react-redux';
import { store } from './app/store';
import { render } from '@testing-library/react';
import App from './App';

test('renders text Just the right amount of everything', () => {

  const { getByText } = render(
    <Provider store={store}>
      <App />
    </Provider>
  );
  const textElement = getByText(/Just the right amount of everything/i);

  expect(textElement).toBeInTheDocument();
});
