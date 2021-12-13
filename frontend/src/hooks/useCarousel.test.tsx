import { renderHook, act } from '@testing-library/react-hooks';
import useCarousel from './useCarousel';

test('carousel auto play', async () => {
  const {
    result,
    waitFor,
  } = renderHook(() => useCarousel([
    { content: 1, bgCover: 'pic 1' },
    { content: 2, bgCover: 'pic 2' },
    { content: 3, bgCover: 'pic 3' },
  ], 1000))
  
  await waitFor(() => {
    return result.current.activeIndex !== 0
  }, { timeout: 1100 })
  
  expect(result.current.pass).toBe(0)
  expect(result.current.activeIndex).toBe(1)
})

test('carousel item change', () => {
  const {
    result
  } = renderHook(() => useCarousel([
    { content: 1, bgCover: 'pic 1' },
    { content: 2, bgCover: 'pic 2' },
    { content: 3, bgCover: 'pic 3' },
  ], 1000))
  
  act(() => {
    result.current.select(2)
  })

  expect(result.current.activeIndex).toBe(2)
})