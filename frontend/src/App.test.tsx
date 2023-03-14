import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('Should render right count slider', () => {
  const renderResult = render(<App />);
  const listItems = renderResult.getAllByRole('listitem');
  expect(listItems).toHaveLength(3)

  const phoneTitle = renderResult.getByText('xPhone');
  expect(phoneTitle).toBeInTheDocument();

  const tabletTitle = renderResult.getByText('Tablet');
  expect(tabletTitle).toBeInTheDocument();

  const airPodTitle = renderResult.getByText('Get airPods');
  expect(airPodTitle).toBeInTheDocument();

});
