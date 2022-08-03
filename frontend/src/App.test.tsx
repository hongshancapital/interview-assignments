import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

it('app渲染测试', () => {
  const { getByText } = render(<App />);
  const linkElement = getByText(/airpods/i);
  expect(linkElement).toBeInTheDocument();
});
