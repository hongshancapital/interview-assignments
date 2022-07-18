import { renderHook, act } from '@testing-library/react-hooks'
import { useCarousel } from './useCarousel'


test('should start interval', async () => {
    const intervalTime = 2000;
    const { result, waitFor } = renderHook(() => useCarousel(4, intervalTime))
    let startIndex = 0;
    expect(result.current.activeIndex).toBe(startIndex)
    await waitFor(() => {
        expect(result.current.activeIndex).toBe(1)
    }, { timeout: intervalTime })
})

test('should handle click', async () => {
    const { result } = renderHook(() => useCarousel(4, 2000))
    expect(result.current.activeIndex).toBe(0)
    act(() => {
        result.current.handleClick(1)
    })
    expect(result.current.activeIndex).toBe(1)
})