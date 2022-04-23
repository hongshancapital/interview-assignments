import { renderHook, act } from '@testing-library/react-hooks'
import { useSwipe } from '../../hooks/useSwipe'
import { fireEvent } from '@testing-library/dom'
import { loop } from '../utils/loop'

describe("useSwipe", () => {
    it('should be defined', () => {
        expect(useSwipe).toBeDefined()
    })

    it('should not call onMove before mousedown', () => {
        const onMove = jest.fn()
        renderHook(() => {
            useSwipe({
                onStart: loop,
                onMove,
                onStop: loop
            })
        })
        act(() => {
            fireEvent.mouseMove(window, {
                clientX: 0,
                clientY: 0
            })
            fireEvent.mouseMove(window, {
                clientX: 10,
                clientY: 10
            })
        })
        expect(onMove).not.toBeCalled()
    })

    it('should not call onMove after mouseup', () => {
        const onMove = jest.fn()
        renderHook(() => {
            useSwipe({
                onStart: loop,
                onMove,
                onStop: loop
            })
        })
        act(() => {
            fireEvent.mouseDown(window, {
                clientX: 0,
                clientY: 0
            })
            fireEvent.mouseUp(window, {
                clientX: 0,
                clientY: 0
            })
            fireEvent.mouseMove(window, {
                clientX: 0,
                clientY: 0
            })
            fireEvent.mouseMove(window, {
                clientX: 10,
                clientY: 10
            })
        })
        expect(onMove).not.toBeCalled()
    })

    it('should call callbacks', () => {
        const onStart = jest.fn()
        const onMove = jest.fn()
        const onStop = jest.fn()
        renderHook(() => {
            useSwipe({
                onStart,
                onMove,
                onStop
            })
        })
        act(() => {
            fireEvent.mouseDown(window, {
                clientX: 0,
                clientY: 0
            })
            fireEvent.mouseMove(window, {
                clientX: 0,
                clientY: 0
            })
            fireEvent.mouseMove(window, {
                clientX: 10,
                clientY: 10
            })
            fireEvent.mouseUp(window, {
                clientX: 10,
                clientY: 10
            })
        })
        expect(onStart).toBeCalled()
        expect(onMove).toBeCalled()
        expect(onStop).toBeCalled()
    })

    it('should call onMove with correct percent', () => {
        const onStart = loop
        const onMove = jest.fn()
        const onStop = loop
        renderHook(() => {
            useSwipe({
                onStart,
                onMove,
                onStop
            })
        })
        act(() => {
            fireEvent.mouseDown(window, {
                clientX: 0,
                clientY: 0
            })
            fireEvent.mouseMove(window, {
                clientX: 0,
                clientY: 0
            })
            fireEvent.mouseMove(window, {
                clientX: 10,
                clientY: 10
            })
            fireEvent.mouseUp(window, {
                clientX: 10,
                clientY: 10
            })
        })
        expect(onMove).toBeCalled()
        expect(onMove).toBeCalledWith(10 / window.innerWidth * 2)
    })
})