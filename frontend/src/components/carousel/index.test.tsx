import React from 'react';
import {render, cleanup } from '@testing-library/react';
import userEvent from "@testing-library/user-event";
import Carousel from './index';
import iphonePNG from '../../assets/iphone.png';
import tabletPNG from '../../assets/tablet.png';
import airpodsPNG from '../../assets/airpods.png';

describe('Carousel Component', () => {
  let items = [
    {
      title: ['xPhone'],
      content: ['Lots to love. Less to spend.', 'Starting at $399'],
      image: iphonePNG,
      color: '#fff',
      bgColor: '#111'
    },
    {
      title: ['Tablet'],
      content: ['Just the right amount of everything'],
      image: tabletPNG,
      bgColor: '#fafafa',
    },
    {
      title: ['Buy a Tablet or xPhone for college.', 'Get airPods'],
      image: airpodsPNG,
      bgColor: '#f1f1f3'
    }
  ];

  afterEach(() => {
    cleanup();
  });

  test('Carousel Render', () => {
    const { container } = render(<Carousel items={items} />);
    const itemCount = container.querySelectorAll('.carousel-item').length;
    const buttonCount = container.querySelectorAll('.carousel-button').length;

    // 节点数量
    expect(itemCount).toBe(items.length);
    expect(buttonCount).toBe(items.length);
  });

  test('With current prop', () => {
    const { container } = render(<Carousel items={items} current={1} duration={100000} />);
    const carouselItems = container.querySelectorAll('.carousel-item');
    const buttons = container.querySelectorAll('.carousel-button');

    expect(carouselItems[1]).toHaveClass('carousel-item-active');
    expect(buttons[1]).toHaveClass('carousel-button-active');
  });

  test('Click Event', () => {
    const { container } = render(<Carousel items={items} duration={100000} />);
    const carouselItems = container.querySelectorAll('.carousel-item');
    const buttons = container.querySelectorAll('.carousel-button');

    userEvent.click(buttons[2]);

    expect(carouselItems[2]).toHaveClass('carousel-item-active');
    expect(buttons[2]).toHaveClass('carousel-button-active');
  });
});
