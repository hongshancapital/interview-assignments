import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

/* 组件测试见tests文件夹 */

test('renders learn react link', () => {
  const { getByText } = render(<App />);
  jest.useFakeTimers();
  const linkElement = getByText(/Get arPods/i);
  expect(linkElement).toBeInTheDocument();
});
