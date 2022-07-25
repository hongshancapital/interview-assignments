
import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Indicators from './indicators';
import style from './index.module.scss';

test('点击indicator会触发onSelect事件', () => {
  const onSelect = jest.fn();

  const { getAllByRole } = render(
    <Indicators activeIndex={0} count={3} progressDuration={3} onSelect={onSelect} />
  );

  fireEvent.click(getAllByRole('link')[0], {});

  expect(onSelect).toHaveBeenCalledTimes(1);
  expect(onSelect).toHaveBeenCalledWith(0);
});

test('根据count会渲染出对应数量的indicator的节点', () => {
  const onSelect = jest.fn();
  const count = 6;
  const { getAllByRole } = render(
    <Indicators activeIndex={0} count={count} progressDuration={3} onSelect={onSelect} />
  );

  const allLinks = getAllByRole('link')

  expect(allLinks).toHaveLength(count)
});

test('根据初始化的activeIndex给节点添加active的样式', () => {
  const onSelect = jest.fn();
  const activeIndex = 1;
  const { getAllByRole } = render(
    <Indicators activeIndex={activeIndex} count={6} progressDuration={3} onSelect={onSelect} />
  );

  const allLinks = getAllByRole('link')

  expect(allLinks[activeIndex]).toHaveClass(style.IndicatorActive)
});
