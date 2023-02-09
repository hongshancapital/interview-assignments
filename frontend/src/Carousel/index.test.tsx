import React from 'react';
import { render } from '@testing-library/react';
import Cmp from './index';

test('正确展示轮播卡片', () => {
  const id = Math.random()
  const { getByTestId } = render(<Cmp list={[
    {
      content: <div data-testid={id}></div>
    }
  ]} />);
  const element = getByTestId(id)
  expect(element).toBeInTheDocument();
});

test('只有一页轮播时，不显示进度条', ()=>{
  const { queryByTestId } = render(<Cmp list={[
    {
      content: <>test</>
    },
  ]} />);
  const element = queryByTestId('progress-bar')
  expect(element).toBeNull()
})