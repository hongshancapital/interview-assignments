import React from 'react';
import { render, screen, act } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { Carousel } from './index';
import { mockData } from './mock';
import { switchTime } from './config';

describe('carousel item switch correctly', () => {
  test('timer switch', async () => {
    jest.useFakeTimers();
    render(<Carousel dataInfo={mockData} />);

    act(() => {
      jest.advanceTimersByTime(switchTime);
    });

    const firstPage = screen.getByText(
      mockData[0].title.toString()
    ).parentElement;

    const secondPage = screen.getByText(
      mockData[1].title.toString()
    ).parentElement;

    expect(secondPage).toHaveStyle(`left: 0`);
    expect(firstPage).toHaveStyle('left: -100%');

    act(() => {
      jest.runOnlyPendingTimers();
    });
    jest.useRealTimers();
  });

  test('click switch', async () => {
    const { container } = render(<Carousel dataInfo={mockData} />);
    const carousel = container.firstElementChild as Element;

    const firstPage = screen.getByText(
      mockData[0].title.toString()
    ).parentElement;

    const secondPage = screen.getByText(
      mockData[1].title.toString()
    ).parentElement;

    expect(firstPage).toHaveStyle('left: 0');
    expect(secondPage).toHaveStyle(`left: 100%`);

    await userEvent.click(carousel);
    expect(secondPage).toHaveStyle(`left: 0`);
    expect(firstPage).toHaveStyle('left: -100%');

    await userEvent.click(carousel);
    await userEvent.click(carousel);
    expect(firstPage).toHaveStyle('left: 0');
    expect(secondPage).toHaveStyle(`left: 100%`);
  });
});
