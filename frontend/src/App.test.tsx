import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders slide', () => {
  const { getAllByTestId } = render(<App />);
  const btn_other_Element = getAllByTestId('btn_other');
  expect(btn_other_Element).toHaveLength(2)
  const btn_current_Element = getAllByTestId('btn_current');
  expect(btn_current_Element).toHaveLength(1)
});
