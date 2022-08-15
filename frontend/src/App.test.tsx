import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import App from './App';

// 验证carousel container 出现
test('renders carousel container', () => {
  render(<App />);
  const carouselContainer = document.querySelector('.carousel-container');
  expect(carouselContainer).toBeInTheDocument();
});

// 验证carousel dot出现
test('renders carousel dot', () => {
  render(<App />);
  const dotContainer = document.querySelector('.dot-container');
  expect(dotContainer).toBeInTheDocument();
});

// 验证首个carousel item 出现
test('renders first carousel item', async () => {
  render(<App />);
  let xPhoneEle = await screen.findByText('xPhone');
  expect(xPhoneEle).toBeInTheDocument();
});

// 验证dot进度条更新
test('renders carousel dot processing', async () => {
  render(<App />);
  await waitFor(() => {
    screen.getByText('xPhone');
    let firstDot = document.querySelector(`.dot-item`);
    setTimeout(() => {
      expect(firstDot).toHaveAttribute('style'); //  'background: linear-gradient(to right, white 0%, white 33%, gray 33%, gray 100%)'
    }, 1000);
  }) 
});

// 验证第二个carousel item出现
test('renders second carousel item', async () => {
  render(<App />);
  let tabletEle = await screen.findByText('Tablet');
  expect(tabletEle).toBeInTheDocument();
});

// 验证第三个carouse item出现
test('renders third carousel item', async () => {
  render(<App />);
  let airpodsEle = await screen.findByText('Get airPods.');
  expect(airpodsEle).toBeInTheDocument();
});

// 验证首个carouse item再次出现
test('renders first carousel item rerender', async () => {
  render(<App />);
  await screen.findByText('Get airPods.');
  let xPhoneEle = await screen.findByText('xPhone');
  expect(xPhoneEle).toBeInTheDocument();
});

// 验证dot进度条重新回到首个dot上
test('renders carousel dot reEnterFirst', async () => {
  render(<App />);
  await screen.findByText('Get airPods.');
  await screen.findByText('xPhone');
  let firstDot = document.querySelector(`.dot-item`);
  setTimeout(() => {
    expect(firstDot).toHaveAttribute('style');
  }, 1000);
});