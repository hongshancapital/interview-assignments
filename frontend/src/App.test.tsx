import React from 'react';
import { render } from '@testing-library/react';
import { classNames } from './utils';
import App from './App';


// demo
// test('renders learn react link', () => {
//   const { getByText } = render(<App />);
//   const linkElement = getByText(/learn react/i);
//   expect(linkElement).toBeInTheDocument();
// });

// function test
test('classNames function test: single string class names', () => {
  expect(classNames('a')).toBe('a')
})

test('classNames function test: multiple string class names', () => {
  expect(classNames('a', 'b', 'c')).toBe('a b c')
})

test('classNames function test: single object true', () => {
  expect(classNames({a: true})).toBe('a')
})

test('classNames function test: single object false', () => {
  expect(classNames({a: false})).toBe('')
})

test('classNames function test: mixed complex scene', () => {
  expect(classNames({a: true, b: false}, {c: true, d: false}, 'e')).toBe('a c e')
})
