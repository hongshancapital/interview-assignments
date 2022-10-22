import { renderHook, act } from '@testing-library/react-hooks';
import useCarousel from './useCarousel';
import { carouselList } from '../mock'


test('carousel auto play', async () => {
  const {
    result,
    waitFor,
  } = renderHook(() => useCarousel(carouselList, 1000))
  
  await waitFor(() => {
    return result.current.activeIndex !== 0
  }, { timeout: 1100 })
  
  expect(result.current.pass).toBe(0)
  expect(result.current.activeIndex).toBe(1)
})

