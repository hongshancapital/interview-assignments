import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders learn react link', () => {
  const { getByText } = render(<App />);
  // 没太明白这里的测试用例 是为了什么？ 找到该字符串？
  const linkElement = getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
