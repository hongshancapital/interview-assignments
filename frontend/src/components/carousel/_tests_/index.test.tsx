import { createRef } from 'react';
import { render, fireEvent, act } from '@testing-library/react';
import Carousel, { cn, CarouselRef } from '../index';

describe('[carousel]组件名称: 轮播', () => {
  const slides = [
    <div key={1}>Slide 1</div>,
    <div key={2}>Slide 2</div>,
    <div key={3}>Slide 3</div>,
  ];
  const activeClass = cn('carousel-item-active');

  test('[#1 - Carousel]用例名称：正常渲染 renders carousel with slides and dots', () => {
    const slidesLen = slides.length;
    const { container } = render(<Carousel>{slides}</Carousel>);
    const slideElements = container.querySelectorAll(`.${cn('carousel-item')}`);
    const dotElements = container.querySelectorAll(`.${cn('dot')}`);
    expect(slideElements.length).toBe(slidesLen);
    expect(dotElements.length).toBe(slidesLen);
  });

  test('[#2 - Carousel]用例名称：自动播放 autoplay', () => {
    const defaultIndex = 0;
    jest.useFakeTimers();
    const { container } = render(<Carousel autoplay={true} defaultIndex={defaultIndex}>{slides}</Carousel>);
    act(() => {
      jest.advanceTimersByTime(2000);
    });
    const slideElements = container.querySelectorAll(`.${cn('carousel-item')}`);
    expect(slideElements[defaultIndex + 1]).toHaveClass(cn('carousel-item-active'));
    act(() => {
      jest.advanceTimersByTime(2000);
    });
    // goNext
    expect(slideElements[defaultIndex + 2]).toHaveClass(cn('carousel-item-active'));
    jest.useRealTimers();
  });

  test('[#3 - Carousel]用例名称：变更自动播放间隔时间 gap', () => {
    const gap = 3000;
    jest.useFakeTimers();
    const { container } = render(<Carousel autoplay gap={gap}>{slides}</Carousel>);
    act(() => {
      jest.advanceTimersByTime(gap);
    });
    const slideElements = container.querySelectorAll(`.${cn('carousel-item')}`);
    expect(slideElements[1]).toHaveClass(activeClass);
    act(() => {
      jest.advanceTimersByTime(gap);
    });
    expect(slideElements[2]).toHaveClass(activeClass);
    jest.useRealTimers();
  });

  test('[#4 - Carousel]用例名称：变更初始化播放的元素序号 defaultIndex', () => {
    const defaultIndex = 1;
    const { container } = render(<Carousel autoplay defaultIndex={defaultIndex}>{slides}</Carousel>);
    const slideElements = container.querySelectorAll(`.${cn('carousel-item')}`);
    
    expect(slideElements[0]).not.toHaveClass(activeClass);
    expect(slideElements[1]).toHaveClass(activeClass);
    expect(slideElements[2]).not.toHaveClass(activeClass);
  });

  test('[#5 - Carousel]用例名称：dot点击跳转到指定卡片', async () => {
    const { container } = render(<Carousel>{slides}</Carousel>);
    const slideElements = container.querySelectorAll(`.${cn('carousel-item')}`);
    const dotElements = container.querySelectorAll(`.${cn('dot')}`);
    await fireEvent.click(dotElements[1]);
    expect(slideElements[0]).not.toHaveClass(activeClass);
    expect(slideElements[1]).toHaveClass(activeClass);
    expect(slideElements[2]).not.toHaveClass(activeClass);
  });

  test('[#6 - Carousel]用例名称：ref跳转到下一个/上一个/指定', () => {
    const carouselRef = createRef<CarouselRef>();
    const { container } = render(<Carousel defaultIndex={0} autoplay ref={carouselRef}>{slides}</Carousel>);
    const slideElements = container.querySelectorAll(`.${cn('carousel-item')}`);
    act(() => {
      carouselRef.current?.goNext();
    });
    expect(slideElements[0]).not.toHaveClass(activeClass);
    expect(slideElements[1]).toHaveClass(activeClass);
    expect(slideElements[2]).not.toHaveClass(activeClass);
    act(() => {
      carouselRef.current?.goPrev();
    })
    expect(slideElements[0]).toHaveClass(activeClass);
    expect(slideElements[1]).not.toHaveClass(activeClass);
    expect(slideElements[2]).not.toHaveClass(activeClass);
    const specialIndex = 2;
    act(() => {
      carouselRef.current?.goTo(specialIndex);
    })
    expect(slideElements[0]).not.toHaveClass(activeClass);
    expect(slideElements[1]).not.toHaveClass(activeClass);
    expect(slideElements[2]).toHaveClass(activeClass);
  });
      
  test('[#7 - Carousel]用例名称：afterChange调用', async () => {
    const afterChange = jest.fn();
    const { container } = render(<Carousel autoplay afterChange={afterChange}>{slides}</Carousel>);
    const dotElements = container.querySelectorAll(`.${cn('dot')}`);
    await fireEvent.click(dotElements[1]);
    expect(afterChange).toHaveBeenCalledWith(1);
  });
});