import React from 'react';
import Carousel from './index';
import { render } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
const CAROUSEL_DEFAULT = [
  {
    key: 'phone',
    title: 'xPhone',
    description: 'Lots to love. Less to spend.\nStarting at $399.',
    illustration:
      'https://raw.githubusercontent.com/scdt-china/interview-assignments/master/frontend/src/assets/iphone.png',
  },
  {
    key: 'tablet',
    title: 'Tablet',
    description: 'Just the right amount of everything.',
    illustration:
      'https://raw.githubusercontent.com/scdt-china/interview-assignments/master/frontend/src/assets/tablet.png',
  },
  {
    key: 'airpods',
    title: 'Buy a Tablet or xPhone for college.\nGet airPods.',
    illustration:
      'https://raw.githubusercontent.com/scdt-china/interview-assignments/master/frontend/src/assets/airpods.png',
  },
];
it('测试Carousel组件', async () => {
  const { container } = render(<Carousel dataSource={CAROUSEL_DEFAULT} />);

  const innerDiv = container.querySelector('.carousel-inner') as HTMLDivElement;
  expect(innerDiv).toBeTruthy();
  expect(innerDiv?.childElementCount).toEqual(3);
  expect(innerDiv?.style?.width).toEqual('300%');

  const ctrlbar = container.querySelector('.ctrl-bar') as HTMLDivElement;
  expect(ctrlbar).toBeTruthy();
  expect(ctrlbar?.childElementCount).toEqual(3);

  const activeDot = ctrlbar.querySelector('.ctrl-dot.active');
  expect(activeDot).toBeTruthy();

  const secondCtrlDot = ctrlbar?.childNodes?.[1];
  expect(secondCtrlDot).toBeTruthy();
  await userEvent.click(secondCtrlDot as Element);
  expect(innerDiv?.style?.transform).toEqual(`translate(-${100 / 3}%, 0)`);
});