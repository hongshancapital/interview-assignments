import { renderHook } from '@testing-library/react-hooks'
import { useTimer } from '../../hooks/userTimer'
import { TestScheduler } from 'rxjs/testing'
import { delay, mergeMap } from 'rxjs'

describe('useTimer', () => {
    let testScheduler: TestScheduler

    beforeEach(() => {
        testScheduler = new TestScheduler((actual, expected) => {
            expect(actual).toEqual(expected)
        })
    })

    it('pause should work', () => {
        testScheduler.run(({ animate, cold, expectObservable }) => {
            const { result } = renderHook(() => {
                return useTimer()
            })
            animate('            ---x---x---x')
            const mapped = cold('-m          ')
            const expected = '   ---a--------'
            const subs = '       ^----------!'

            mapped.pipe(
                delay(5)
            ).subscribe(
                () => result.current.pause()
            )
            expectObservable(
                mapped.pipe(mergeMap(
                    () => result.current.timer$
                ))
                , subs
            ).toBe(expected, { a: 2 })
        })
    })

    it('restartFrom should work', () => {
        testScheduler.run(({ animate, cold, expectObservable }) => {
            const { result } = renderHook(() => {
                return useTimer()
            })
            animate('            ---x---x---x')
            const mapped = cold('-m          ')
            const expected = '   ---a---b---c'
            const subs = '       ^----------!'

            mapped.pipe(
                delay(5)
            ).subscribe(
                () => result.current.restartFrom(100)
            )
            expectObservable(
                mapped.pipe(mergeMap(
                    () => result.current.timer$
                ))
                , subs
            ).toBe(expected, { a: 2, b: 101, c: 105 })
        })
    })

    it('advance should work', () => {
        testScheduler.run(({ animate, cold, expectObservable }) => {
            const { result } = renderHook(() => {
                return useTimer()
            })
            animate('            ---x---x---x')
            const mapped = cold('-m          ')
            const expected = '   ---a--b------'
            const subs = '       ^----------!'

            mapped.pipe(
                delay(5)
            ).subscribe(
                () => {
                    result.current.pause()
                    result.current.advance(10)
                }
            )
            expectObservable(
                mapped.pipe(mergeMap(
                    () => result.current.timer$
                ))
                , subs
            ).toBe(expected, { a: 2, b: 12 })
        })
    })
})
