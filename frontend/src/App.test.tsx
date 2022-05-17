/*
 * @Author: shiguang
 * @Date: 2022-05-17 18:04:42
 * @LastEditors: shiguang
 * @LastEditTime: 2022-05-17 19:29:34
 * @Description: carousel 页面测试
 */
import React from 'react';
import { render } from '@testing-library/react';
import App, { renderSlider } from './App';

describe('carousel page', () => {
  it('test slider item', () => {
    const itemConf = {
      name: 'xPhone',
      title: ['xPhone'],
      desc: ['Lots to love.Less to spend.', 'Staring ar $399.'],
    };
    const { getByText } = render(
      renderSlider(itemConf, 0)
    );
    const titleDivEl = getByText('xPhone');
    const descDivEl = getByText('Lots to love.Less to spend.');

    expect(titleDivEl).toBeInTheDocument();
    expect(descDivEl).toBeInTheDocument();

    expect(titleDivEl.parentElement?.classList).toContain(
      'carousel-item-text-title'
    );
    expect(descDivEl.parentElement?.classList).toContain(
      'carousel-item-text-desc'
    );
  });

  it('test carousel page', () => {
    render(<App />);

    expect(document.querySelector('.app-page')).toBeInTheDocument();
    const carouselItemList = document.querySelectorAll('.carousel-item');

    expect(carouselItemList.length).toBe(3);
  });

});
