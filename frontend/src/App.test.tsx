import React from 'react';
import { act, render } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import useActiveIndex from './carousel/useActiveIndex';
import Dots from './carousel/dots';

jest.useFakeTimers();

describe('carousel component unit testing', () => {
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

  test('dot component', () => {
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
});
