import React from 'react';
import { act, render,screen, renderHook, fireEvent } from '@testing-library/react';
import Carousel from './Carousel';

test('Carousel 测试第一张图的文本',()=>{
  render(<Carousel />);
  const linkElement = screen.getByText('xPhone');
  expect(linkElement).toBeInTheDocument();
})
test('Carousel 测试第二张图的文本',()=>{
  render(<Carousel />);
  const linkElement = screen.getByText('Tablet');
  expect(linkElement).toBeInTheDocument();
})
test('Carousel 测试第三张图的文本',()=>{
  render(<Carousel />);
  const linkElement = screen.queryByText('airPods');
  expect(linkElement).toBeNull();
})
