import React from 'react';
import { act, fireEvent, render, renderHook } from '@testing-library/react';
import Carousel from './index';
import { classNames, useUid } from './utils';

describe('utils test', () => {
  test('test classNames', () => {
    expect(classNames()).toBe('');
    expect(classNames('a')).toBe('a');
    expect(classNames({ a: true })).toBe('a');
    expect(classNames('a', '', { b: true }, { c: false, d: true })).toBe('a b d');
  });

  test('test useUid', () => {
    const { result } = renderHook(() => useUid());
    const { result: another_result } = renderHook(() => useUid());
    expect(result.current).toMatch(/^[0-9a-z]{5}$/);
    expect(result.current).not.toBe(another_result.current);
  });
});

describe('carousel component test', () => {
  test('renders Carousel component', () => {
    const { getByText } = render(<Carousel>
      <Carousel.Item>1</Carousel.Item>
      <Carousel.Item>2</Carousel.Item>
    </Carousel>);
    const element = getByText('1');
    expect(element).toBeInTheDocument();
  });
  
  test('test autoplay', async () => {
    const { getAllByRole } = render(<Carousel
      autoplay
      duration={2000}
    >
      <div>1</div>
      <div>2</div>
    </Carousel>);
    const tabpanels = getAllByRole('tabpanel', {
      hidden: true,
    });
    expect(tabpanels[0]).toHaveAttribute('aria-hidden', 'true');
    expect(tabpanels[1]).toHaveAttribute('aria-hidden', 'false');
    await act(() => {
      return new Promise(resolve => setTimeout(resolve, 2000));
    });
    expect(tabpanels[0]).toHaveAttribute('aria-hidden', 'false');
    expect(tabpanels[1]).toHaveAttribute('aria-hidden', 'true');
  });

  test('test indicator click', () => {
    const { getByRole, container } = render(<Carousel>
      <div>1</div>
      <div>2</div>
    </Carousel>);
    const targetTab = getByRole('tab', {
      selected: false,
    });
    const targetTabpanel = container.querySelector(`#${targetTab.getAttribute('aria-controls')}`);
    expect(targetTab).toHaveAttribute('aria-selected', 'false');
    expect(targetTabpanel).toHaveAttribute('aria-hidden', 'false');
    fireEvent.click(targetTab);
    expect(targetTab).toHaveAttribute('aria-selected', 'true');
    expect(targetTabpanel).toHaveAttribute('aria-hidden', 'true');
  });

  test('test callback function', async () => {
    const handleChange = jest.fn();
    const { getByRole } = render(<Carousel
      autoplay
      duration={2000}
      onChange={handleChange}
    >
      <div>1</div>
      <div>2</div>
    </Carousel>);
    const targetTab = getByRole('tab', {
      selected: false,
    });
    fireEvent.click(targetTab);
    expect(handleChange).toHaveBeenCalledTimes(1);
    expect(handleChange).toHaveBeenCalledWith(1, 0);
  })
});