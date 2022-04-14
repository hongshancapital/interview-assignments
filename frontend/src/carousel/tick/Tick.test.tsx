import React from 'react';
import { render } from '@testing-library/react';
import Tick from './Tick';

test('renders learn react link', () => {
  const { getByText } = render(<Tick />);
  const linkElement = getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});