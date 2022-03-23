import React from 'react';
import {render, cleanup } from '@testing-library/react';
import Carousel from './Carousel';

describe('Carousel Component', () => {

  afterEach(() => {
    cleanup();
  });

  test('Carousel Render', () => {
    const app = (
      <Carousel>
        <div className="content-item item-one">
          <h2 className="title">xPhone</h2>
          <div className="text">Lots to love. Less to spend.</div>
          <div className="text">Sarting at $399.</div>
        </div>
        <div className="content-item item-two">
          <h2 className="title">Tablet</h2>
          <div className="text">Just th right amount of everything.</div>
        </div>
        <div className="content-item item-three">
          <h2 className="title">Buy a Tablet or xPhone for college.</h2>
          <h2 className="title">Get arPods.</h2>
        </div>
      </Carousel>
    );
    const { container } = render(app);
    const contentItems = container.querySelectorAll('.content-item');
    const indicators = container.querySelectorAll('.carousel-container .indicator-item');

    expect(contentItems.length).toBe(3); // 内容数量
    expect(indicators.length).toBe(3);   // 指示器数量
    expect(indicators[0]).toHaveClass('active');
    expect(indicators[1]).toHaveClass('default');
    expect(indicators[2]).toHaveClass('default');
  });
});