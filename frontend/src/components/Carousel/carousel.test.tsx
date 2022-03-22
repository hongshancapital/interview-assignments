import React from 'react';
import { render } from '@testing-library/react';
import { Carousel } from './index';
import { defaultData } from './data'

test('renders learn react link', () => {
  const { queryAllByText } = render(<Carousel data={defaultData} />);
  const eleElement = queryAllByText(/xPhone/g);
  expect(eleElement.length).toEqual(2);
});
