import React from 'react';
import { act, render, screen } from '@testing-library/react';
import { renderHook } from '@testing-library/react-hooks';
import airpods from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import useActiveIndex from './carousel/useActiveIndex';
import Slider from './carousel/slider'; 
import Dots from './carousel/dots';

jest.useFakeTimers();

const data=[{
  title: ['xPhone'],
  desc: ['Lots to love.Less to spend.', 'Staring ar $399.'],
  img: iphone,
  color: '#fff'
}, {
  title: ['Tablet'],
  desc: ['Just the right amount of everything.'],
  img: tablet,
  color: '#000'
}, {
  title: ['Buy a Tablet or xPhone for college.', 'Get arPords.'],
  img: airpods,
  color: '#000'
}];

describe('carousel component unit testing', () => {
  test('useActiveIndex hooks', () => {
    const { result } = renderHook(() => useActiveIndex({ count: 3 }));
    expect(result.current).toBe(0);

    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(result.current).toBe(1);

    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(result.current).toBe(2);

    act(() => {
      jest.runOnlyPendingTimers();
    });
    expect(result.current).toBe(0);

    jest.clearAllTimers();
  });

  test('slider component show first data when curIndex = 0', () => {
    const curIndex = 0;

    act(() => {
      render(<Slider data={data} curIndex={curIndex} />);
    });

    data[curIndex].title.forEach(t => {
      const title = screen.getByText(t);
      expect(title).toBeInTheDocument();
    });

    data[curIndex].desc?.forEach(d => {
      const desc = screen.getByText(d);
      expect(desc).toBeInTheDocument();
    });
  });

  test('slider component show second data when curIndex = 1', () => {
    const curIndex = 1;

    act(() => {
      render(<Slider data={data} curIndex={curIndex} />);
    });

    data[curIndex].title.forEach(t => {
      const title = screen.getByText(t);
      expect(title).toBeInTheDocument();
    });

    data[curIndex].desc?.forEach(d => {
      const desc = screen.getByText(d);
      expect(desc).toBeInTheDocument();
    });
  });

  test('slider component show third data if curIndex = 2', () => {
    const curIndex = 2;

    act(() => {
      render(<Slider data={data} curIndex={curIndex} />);
    });

    data[curIndex].title.forEach(t => {
      const title = screen.getByText(t);
      expect(title).toBeInTheDocument();
    });

    data[curIndex].desc?.forEach(d => {
      const desc = screen.getByText(d);
      expect(desc).toBeInTheDocument();
    });
  });

  test('dot component', () => {
    const props = {
      count: 3,
      duration: 3000,
      curIndex: 0
    }

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
