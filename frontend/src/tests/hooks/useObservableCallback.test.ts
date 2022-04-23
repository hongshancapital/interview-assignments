import { renderHook, act } from '@testing-library/react-hooks'
import { Subject } from 'rxjs'
import { useObservableCallback } from '../../hooks/useObservableCallback'

const mockObservable$ = new Subject<number>()

describe('useObservable', () => {
    it('should call callback', () => {
        const mockCallback = jest.fn()
        renderHook(() => {
            useObservableCallback(mockObservable$, mockCallback)
        })
        act(() => {
            mockObservable$.next(1)
        })
        expect(mockCallback).toBeCalled()
        expect(mockCallback).toBeCalledWith(1)
    })

    it('should call latest callback', () => {
        const mockCallback1 = jest.fn()
        const mockCallback2 = jest.fn()
        const { rerender } = renderHook((props) => {
            useObservableCallback(mockObservable$, props.cb)
        }, {
            initialProps: {
                cb: mockCallback1
            }
        })
        act(() => {
            mockObservable$.next(1)
        })
        expect(mockCallback1).toBeCalled()
        mockCallback1.mockClear()
        act(() => {
            rerender({ cb: mockCallback2 })
        })
        act(() => {
            mockObservable$.next(2)
        })
        expect(mockCallback1).not.toBeCalled()
        expect(mockCallback2).toBeCalled()
        expect(mockCallback2).toBeCalledWith(2)
    })

    it('should unsubscribe when unmount', () => {
        const mockCallback = jest.fn()
        const { unmount } = renderHook(() => {
            useObservableCallback(mockObservable$, mockCallback)
        })
        act(() => {
            mockObservable$.next(1)
        })
        expect(mockCallback).toBeCalled()
        expect(mockCallback).toBeCalledWith(1)
        mockCallback.mockClear()
        act(() => {
            unmount()
        })
        act(() => {
            mockObservable$.next(2)
        })
        expect(mockCallback).not.toBeCalled()
    })
})