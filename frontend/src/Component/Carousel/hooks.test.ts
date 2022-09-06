/*
 * @Author: shiguang
 * @Date: 2022-05-17 19:20:30
 * @LastEditors: shiguang
 * @LastEditTime: 2022-05-18 10:33:18
 * @Description: hooks 相关测试
 */
import { useEffect, useState } from 'react';
import { act } from 'react-dom/test-utils';
import { renderHook } from '@testing-library/react-hooks';
import { useCarouselAnimate, useEvent } from './hooks';

describe('hooks test', () => {
    it('test useEvent hook', () => {
        const useTestUseEvent = () => {
            const [closureNumber, setClosureNumber] = useState(0);
            const [eventFnExecCount, setEventFnExecCount] = useState(0);
            const fn = useEvent(() => {
                return closureNumber;
            });

            // 计算引用是否一致，进入 useEffect 多次说明引用不一致
            useEffect(() => {
                fn();
                setEventFnExecCount((n) => n + 1);
            }, [fn]);

            // 模拟 多次 render
            useEffect(() => {
                if (closureNumber >= 2) {
                    setClosureNumber(3);
                    return;
                }
                setTimeout(() => {
                    setClosureNumber((n) => n + 1);
                }, 1000);
            }, [closureNumber]);
            return {
                isAddressEqual: eventFnExecCount === 1,
                // 最终 closureNumber 值应为 3 非初始值 0
                memoFn: fn,
            };
        };
        jest.useFakeTimers();
        const spySetTimeout = jest.spyOn(window, 'setTimeout');
        const { result } = renderHook(() => {
            return useTestUseEvent();
        });
        act(() => {
            jest.runOnlyPendingTimers();
            jest.runOnlyPendingTimers();
            jest.runOnlyPendingTimers();
            jest.runOnlyPendingTimers();
        });

        expect(spySetTimeout).toHaveBeenCalled();
        expect(result.current.memoFn()).not.toBe(0);
        expect(result.current.isAddressEqual).toBe(true);
    });

    it('test useCarouselAnimate hook', () => {
        const { result } = renderHook(() => {
            return useCarouselAnimate({
                initIndex: 0,
                sliderCount: 3,
                duration: 1000,
            });
        });
        expect(result.current.curIndex).toBe(0);
        act(() => {
            result.current.next()
        })
        expect(result.current.curIndex).toBe(1);
        act(() => {
            result.current.next()
        })
        expect(result.current.curIndex).toBe(2);
        act(() => {
            result.current.next()
        })
        expect(result.current.curIndex).toBe(0);

        act(() => {
            result.current.goTo(2);
        })
        expect(result.current.curIndex).toEqual(2);
    });

});
