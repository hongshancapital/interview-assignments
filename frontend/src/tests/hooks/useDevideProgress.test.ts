import { renderHook } from '@testing-library/react-hooks'
import { TestScheduler } from 'rxjs/testing'
import { mergeMap } from 'rxjs'
import { useDevideProgress } from '../../hooks/useDevideProgress'

describe('useDevideProgress', () => {
    let testScheduler: TestScheduler

    beforeEach(() => {
        testScheduler = new TestScheduler((actual, expected) => {
            expect(actual).toEqual(expected)
        })
    })

    it('all devided progress should work', () => {
        testScheduler.run(({ animate, cold, expectObservable }) => {
            const { result } = renderHook(() => {
                return useDevideProgress(10, 3)
            })
            animate('            --x---x---x---x---x---x---x---x')
            const mapped = cold('-m          ')
            const expected1 = '  --a---b---c---d'
            const expected2 = '  --a-----------b---c---d'
            const expected3 = '  --a-------------------b---c---d'
            const subs = '       ^            30ms             !'

            expectObservable(
                mapped.pipe(mergeMap(
                    () => result.current[0]
                ))
                , subs
            ).toBe(expected1, { a: 10, b: 50, c: 90, d: 0 })
            expectObservable(
                mapped.pipe(mergeMap(
                    () => result.current[1]
                ))
                , subs
            ).toBe(expected2, { a: 0, b: 30, c: 70, d: 0 })
            expectObservable(
                mapped.pipe(mergeMap(
                    () => result.current[2]
                ))
                , subs
            ).toBe(expected3, { a: 0, b: 10, c: 50, d: 90 })
        })
    })
})
