import React from 'react';
import { act, render, screen, fireEvent } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import useActiveIndex from './carousel/useActiveIndex';
import Dots from './carousel/dots';
import App from './App';

jest.useFakeTimers();

describe('carousel component unit testing', () => {
  test('snapshot', () => {
    const { container } = render(<App />);
    expect(container).toMatchSnapshot();
  });
  test('useActiveIndex hooks', () => {
    const { result } = renderHook(() => useActiveIndex({ count: 3 }));
    expect(result.current.curIndex).toBe(0);

    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(result.current.curIndex).toBe(1);

    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(result.current.curIndex).toBe(2);

    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(result.current.curIndex).toBe(0);

    jest.clearAllTimers();
  });

  test('dot component', async () => {
    const { result } = renderHook(() => useActiveIndex({ count: 3 }));

    const props = {
      count: 3,
      duration: 3000,
      curIndex: 0,
      setCurIndex: result.current.setCurIndex,
    };

    act(() => {
      render(<Dots {...props} />);
    });

    const dot = document.querySelector('.dotContainer');
    expect(dot).toBeInTheDocument();

    const dotBoxs = document.querySelectorAll('.dotBox');
    expect(dotBoxs.length).toBe(3);

    const dotCenter = document.querySelectorAll('.dotCenter.active');
    expect(dotCenter.length).toBe(1);
  });

  test('dot click', async () => {
    render(<App />);

    const carousel = await screen.findByTestId('carousel');
    expect(carousel.classList.contains('active-0')).toBe(true);
    const dot1 = await screen.findByTestId('dot_1');
    fireEvent.click(dot1);
    expect(carousel.classList.contains('active-1')).toBe(true);
  });
});
