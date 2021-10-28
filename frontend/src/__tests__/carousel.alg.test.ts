import {next,prev} from "../component/Carousel/useCarousel"

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

test('use-carousel', () => {


})