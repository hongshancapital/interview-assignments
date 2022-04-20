

import React from 'react';
import { renderHook } from '@testing-library/react-hooks'
import { act, render, screen } from '@testing-library/react';
import { useActiveIndex } from '../hooks';
import CarouselItem from '../carouselItem';
import Progress from '../progress';
import Carousel from '../index';

jest.useFakeTimers();

describe('carousel component test', () => {

  test('activeIndex hook', () => {
    const { result } = renderHook(() => useActiveIndex({ count: 3}));
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

  it("render carouselItem component", () => {
    const props = {
      src: '',
      title: ["xPhone"],
      desc: ["Lots to love. Less to spend.", "Starting at $399."],
    };
    act(() => {
      render(<CarouselItem {...props} />);
    });

    props.title.forEach((c) => {
        const title = screen.getByText(c);
        expect(title).toBeInTheDocument();
      });
    props.desc?.forEach((c) => {
      const desc = screen.getByText(c);
      expect(desc).toBeInTheDocument();
    });
  });

  it("render progress component", () => {
    const props = {
      count: 3,
      time: 3000,
      activeIndex: 0,
    };
    act(() => {
      render(<Progress {...props} />);
    });

    const progressElem = document.querySelector(".carousel-progress");
    expect(progressElem).toBeInTheDocument();

    const items = document.querySelectorAll(".carousel-progress-item");
    expect(items.length).toBe(3);
  });

  it("render carousel component", () => {
    const props = {
      items: [{
        src: '',
        title: ["xPhone"],
        desc: ["Lots to love. Less to spend.", "Starting at $399."],
        color: 'white',
      }, {
        src: '',
        title: ["Tablet"],
        desc: ["Just the right amount of everything."],
      }, {
        src: '',
        title: ["Buy a Tablet or xPhone for college.", "Get arPods."],
      }]
    };
    act(() => {
      render(<Carousel {...props} />);
    });

    const item = document.querySelector(".carousel");
    expect(item).toBeInTheDocument();

    const items = document.querySelectorAll(".carousel-items");
    expect(items.length).toBe(3);
  });

});