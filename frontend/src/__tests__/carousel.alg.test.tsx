import React, { useEffect, useState } from "react";
import { act, render } from "@testing-library/react";
import {calcTransition, next,prev, useCarousel} from "../component/Carousel/useCarousel"
import { TransitionState } from "../hook/useCSSTransition";
import { AsyncUtil } from "../util/AsyncUtil";

/**
 * 测试核心算法
 */
test('next-prev', () => {
  expect(next(0, 3)).toBe(1)
  expect(prev(0, 3)).toBe(2)
  expect(prev(1, 3)).toBe(0)
  expect(next(2, 3)).toBe(0)
});

test('next-prev-boundary', () => {
  expect(next(0, 1)).toBe(0)
  expect(prev(0, 1)).toBe(0)
  expect(next(0, 0)).toBe(0)
})

test('use-carousel', async () => {

  
  function T1(){
    const {state, position, scrollerStyle} = useCarousel(3, 10, 10)
    useEffect(() => {
      const trans = calcTransition(position, 3, 10)
      switch(state) {
        case TransitionState.PREPARE:
          expect(scrollerStyle).toEqual(trans.prepare)
          break
        case TransitionState.ENTER:
          expect(scrollerStyle).toEqual(trans.enter)
          break
        case TransitionState.LEAVE:
          expect(scrollerStyle).toEqual(trans.leave)
          break
      }
    }, [state])
    return <div></div>
  }


  render(<T1 />)
  await act(async () => {
    await AsyncUtil.wait(150)
  })

})