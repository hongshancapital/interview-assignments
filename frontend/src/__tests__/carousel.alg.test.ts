import {next,prev} from "../component/Carousel/AbstractAlg"
import {OneSideAlg, InfiniteLoopAlg} from '../component/Carousel/Algs'

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


test("alg-loop-right", () => {
  const alg = new InfiniteLoopAlg({
    current : 0,
    dir : "right",
    duration : 10,
    size : 5
  })

  expect(alg.getCurrent()).toBe(0)
  expect(alg.viewport()).toEqual([4,0,1])
  alg.next()
  expect(alg.getCurrent()).toBe(1)
  expect(alg.viewport()).toEqual([0,1,2])

  expect(alg.transitionStyle()).toEqual(  {
    prepare: { transform: 'translateX(-100%)' },
    enter: { transform: 'translateX(-200%)', transition: 'transform 0.01s ease' },
    leave: { transform: 'translateX(-200%)' }
  })
})

test("alg-loop-left", () => {
  const alg = new InfiniteLoopAlg({
    current : 0,
    dir : "left",
    duration : 10,
    size : 5
  })

  expect(alg.getCurrent()).toBe(0)
  expect(alg.viewport()).toEqual([4,0,1])
  alg.next()
  expect(alg.getCurrent()).toBe(4)
  expect(alg.viewport()).toEqual([3,4,0])

  expect(alg.transitionStyle()).toEqual(  {
    prepare: { transform: 'translateX(-100%)' },
    enter: { transform: 'translateX(0%)', transition: 'transform 0.01s ease' },
    leave: { transform: 'translateX(0%)' }
  })
})



test("alg-oneside-right", () => {

  const alg = new OneSideAlg({
    current : 0,
    dir : 'right',
    duration : 10,
    size : 3
  })

  expect(alg.viewport()).toEqual([0, 1, 2])
  expect(alg.getCurrent()).toBe(0)
  expect(alg.transitionStyle()).toEqual({
    prepare: { transform: "translateX(0%)" },
    enter: {
      transform: "translateX(-100%)",
      transition: "transform 0.01s ease",
    },
    leave: { transform: "translateX(-100%)" },
  })
  alg.next()
  expect(alg.getCurrent()).toBe(1)
  alg.next()
  expect(alg.getCurrent()).toBe(2)
  expect(alg.transitionStyle()).toEqual({
    prepare: { transform: "translateX(-200%)" },
    enter: {
      transform: "translateX(0%)",
      transition: "transform 0.01s ease",
    },
    leave: { transform: "translateX(0%)" },
  })
  alg.next()
  expect(alg.getCurrent()).toBe(0)
})


test("alg-oneside-left", () => {

  const alg = new OneSideAlg({
    current : 0,
    dir : 'left',
    duration : 10,
    size : 3
  })

  expect(alg.viewport()).toEqual([0, 1, 2])
  expect(alg.getCurrent()).toBe(0)
  expect(alg.transitionStyle()).toEqual({
    prepare: { transform: "translateX(0%)" },
    enter: {
      transform: "translateX(-200%)",
      transition: "transform 0.01s ease",
    },
    leave: { transform: "translateX(-200%)" },
  })
  alg.next()
  expect(alg.getCurrent()).toBe(2)
  alg.next()
  expect(alg.getCurrent()).toBe(1)
  expect(alg.transitionStyle()).toEqual({
    prepare: { transform: "translateX(-100%)" },
    enter: {
      transform: "translateX(0%)",
      transition: "transform 0.01s ease",
    },
    leave: { transform: "translateX(0%)" },
  })
  alg.next()
  expect(alg.getCurrent()).toBe(0)
})