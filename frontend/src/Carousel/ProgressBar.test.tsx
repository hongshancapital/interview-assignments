import React from 'react';
import { render } from '@testing-library/react';
import Cmp from './ProgressBar';

const size = 3;
const index = 1;

test('正确展示页码栏', () => {
  const { getByTestId, getAllByTestId } = render(<Cmp size={size} index={index} duration={3000} />);
  const element = getByTestId('progress-bar')
  expect(element).toBeInTheDocument();

  const items = getAllByTestId('progress-item')
  expect(items.length).toBe(size)
});


test('正确展示进度条', ()=>{
  const { getByTestId, getAllByTestId } = render(<Cmp size={size} index={index} duration={3000} />);
  const items = getAllByTestId('progress-item')
  const element = getByTestId('progress')
  expect(items[index]).toContainElement(element)
})