import React from "react";
import { render } from "@testing-library/react";
import { act } from '@testing-library/react-hooks';

import Carousel, {CarouselRef} from '../Carousel'
import Item from '../Item'

describe('Carousel', () => {
  beforeEach(() => {
    jest.useFakeTimers();
  });

  afterEach(() => {
    jest.useRealTimers();
  });

  it('should has prev, next and goTo method', () => {
    const ref = React.createRef<CarouselRef>()
    const Component = () => {
      return <Carousel ref={ref}>
        <Item>1</Item>
        <Item>2</Item>
        <Item>3</Item>
      </Carousel>
    }
    render(<Component />);
    const {prev, next, goTo} = ref.current
    expect(typeof prev).toBe('function')
    expect(typeof next).toBe('function')
    expect(typeof goTo).toBe('function')
    expect(ref.current?.innerCurrent).toBe(0)
    act(() => {
      ref.current?.goTo(1)
    })
    jest.runAllTimers()
    expect(ref.current?.innerCurrent).toBe(1)
    act(() => {
      ref.current?.next()
    })
    jest.runAllTimers()
    expect(ref.current?.innerCurrent).toBe(2)
    act(() => {
      ref.current?.prev()
    })
    jest.runAllTimers()
    expect(ref.current?.innerCurrent).toBe(1)
  })

  it('should active', () => {
    const ref = React.createRef<CarouselRef>()
    const Component = () => {
      return <Carousel ref={ref}>
        <Item>111</Item>
        <Item>222</Item>
        <Item>333</Item>
      </Carousel>
    }
    const {container} = render(<Component />);

    const indicatorList =container.querySelectorAll('.carousel__indicator')
    expect(indicatorList[0].className.indexOf('active') > -1).toBeTruthy()
    act(() => {
      ref.current?.goTo(2)
    })
    expect(indicatorList[2].className.indexOf('active') > -1).toBeTruthy()
  })
})