/*
 * @Author: danjp
 * @Date: 2022/6/25 13:48
 * @LastEditTime: 2022/6/25 13:48
 * @LastEditors: danjp
 * @Description:
 */
import React from 'react';
import Enzyme, { mount } from 'enzyme';
import { act } from 'react-dom/test-utils';
import Adapter from '@wojtekmaj/enzyme-adapter-react-17';
import Carousel, { CarouselProps, CarouselRef } from './Carousel';

Enzyme.configure({ adapter: new Adapter() });

describe('Carousel', () => {
  it('should `initialIndex is not empty`', () => {
    const ref = React.createRef<CarouselRef>() ;
    mount(
      <Carousel ref={ref} initialIndex={2}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>,
    );
    expect(ref.current?.current).toEqual(2);
  });
  
  it('should has prev、next and goTo function', () => {
    const ref = React.createRef<CarouselRef>() ;
    mount(
      <Carousel ref={ref} autoplay={false}>
        <div>1</div>
        <div>2</div>
        <div>3</div>
      </Carousel>,
    );
    const { prev, next, goTo } = ref.current!;
    expect(typeof prev).toBe('function');
    expect(typeof next).toBe('function');
    expect(typeof goTo).toBe('function');
    
    expect(ref.current?.current).toEqual(0);
    
    act(() => {
      ref.current?.goTo(2);
    });
    expect(ref.current?.current).toEqual(2);
  
    act(() => {
      ref.current?.prev();
    });
    expect(ref.current?.current).toEqual(1);
  
    act(() => {
      ref.current?.next();
    });
    expect(ref.current?.current).toEqual(2);
  });
});
