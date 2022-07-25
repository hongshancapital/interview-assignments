import { renderHook, act } from '@testing-library/react-hooks';

import useCarousel from '../useCarousel'

describe('useCarousel', () => {

  it('call the goto method to move to the position width index 1', () => {
    const { result } = renderHook(() => useCarousel({count: 3, duration: 3000, width: 1000}))
    act(()=> {
      result.current.goTo(1)
    })
    expect(result.current.current).toBe(1)
  })

  it('call the next method', () => {
    const { result } = renderHook(() => useCarousel({count: 3, duration: 3000, width: 1000}))
    act(()=> {
      result.current.next()
    })
    expect(result.current.current).toBe(1)
  })

  it('call the next method', () => {
    const { result } = renderHook(() => useCarousel({count: 3, duration: 3000, width: 1000}))
    act(()=> {
      result.current.next()
    })
    expect(result.current.current).toBe(1)
  })

  it('call the prev method', () => {
    const { result } = renderHook(() => useCarousel({count: 3, duration: 3000, width: 1000}))
    act(()=> {
      result.current.next()
    })
    act(()=> {
      result.current.prev()
    })
    expect(result.current.current).toBe(0)
  })
})