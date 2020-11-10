import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  const MainPage = render(<App />);
  expect(MainPage.container.getElementsByClassName('App').length).toEqual(1);
});
