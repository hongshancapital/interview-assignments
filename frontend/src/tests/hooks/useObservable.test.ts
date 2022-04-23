import { renderHook, act } from '@testing-library/react-hooks'
import { Subject } from 'rxjs'
import { useObservable } from '../../hooks/useObservable'

const mockObservable$ = new Subject<number>()

describe('useObservable', () => {
    it('should return initialState', () => {
        const { result } = renderHook(() => {
            return useObservable(mockObservable$, 0)
        })

        expect(result.current).toBe(0)
    })

    it('should emit value from Observable', () => {
        const { result } = renderHook(() => {
            return useObservable(mockObservable$, 0)
        })
        act(() => {
            mockObservable$.next(1)
        })
        expect(result.current).toBe(1)
    })
})