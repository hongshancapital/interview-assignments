import { act, renderHook } from '@testing-library/react-hooks'
import useSwiper from './useSwiper'

describe('useSwiper', () => {
  test('should return initial index and go function', () => {
    const count = 3
    const option = {}
    const { result } = renderHook(() => useSwiper(count, option))

    expect(result.current[0]).toBe(0)
    expect(typeof result.current[1]).toBe('function')
  })

  test('should increment correctly with go function', () => {
    const count = 4
    const option = {}
    const { result } = renderHook(() => useSwiper(count, option))

    act(() => {
      result.current[1](2)
    })

    expect(result.current[0]).toBe(2)
  })

  test('should reset to 0 when the end is reached', () => {
    jest.useFakeTimers()

    const count = 3
    const option = { autoplay: true,delay: 1 }
    const { result } = renderHook(() => useSwiper(count, option))

    act(() => {
      jest.advanceTimersByTime(option.delay * 1000 * count)
    })

    expect(result.current[0]).toBe(0)
  })

  test('should not run timer when autoplay is false', () => {
    jest.useFakeTimers()

    const count = 3
    const option = { autoplay: false, delay: 1 }
    const { result } = renderHook(() => useSwiper(count, option))

    act(() => {
      jest.advanceTimersByTime(option.delay * 1000 * count)
    })

    expect(result.current[0] > 0).toBeFalsy()

  })

  test('should clear timer when go function is called', () => {

    const count = 4
    const option = { autoplay: true }
    const { result } = renderHook(() => useSwiper(count, option))

    act(() => {
      result.current[1](2)
    })

    expect(result.current[0]).toBe(2)
  })
})
