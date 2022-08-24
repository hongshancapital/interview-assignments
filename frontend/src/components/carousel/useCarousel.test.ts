import { next, carouselReducer, CarouselNextAction, CarouselPrgressAction, CarouselDoneAction}  from './useCarousel'

describe("next function", () => {
    test("next should return next number if not out of bound", () => {
        const count = 3;
        expect(next(count, 0)).toBe(1)
        expect(next(count, 1)).toBe(2)
    })

    test("next should return round to zero if  out of bound", () => {
        const count = 3;
        expect(next(count, 2)).toBe(0)
    })
})


describe('carouselReducer function', () => {
    test("next action should only change target state", () => {
        const length = 3;
        const target = 2;
        const current = Math.random() * 4;
        const progress = Math.random() * 100;

        const oldState = {target, current , progress}
        const action = {type: 'next', length } as CarouselNextAction
        const newState = carouselReducer(oldState, action)

        expect(newState.current).toBe(current)
        expect(newState.target).toBe(next(length, current))
        expect(newState.progress).toBe(progress)
    })

    test("done action should only change current state to target value", () => {
        const current = 2;
        const target = 3;
        const progress = Math.random() * 100;

        const oldState = {target, current , progress}
        const action = {type: 'done'} as CarouselDoneAction
        const newState = carouselReducer(oldState, action)

        expect(newState.current).toBe(target)
        expect(newState.target).toBe(target)
        expect(newState.progress).toBe(0)
    })

    test("progress action should only change progress", () => {
        const current = Math.random() * 3;
        const target = Math.random() * 3;
        const progress = 80;

        const oldState = {target, current , progress}
        const action = {type: 'progress', progress} as CarouselPrgressAction
        const newState = carouselReducer(oldState, action)

        expect(newState.current).toBe(current)
        expect(newState.target).toBe(target)
        expect(newState.progress).toBe(progress)
    })
})
