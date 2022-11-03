/** 不支持无返回值自定义hook测试 */
import { renderHook, act } from '@testing-library/react-hooks'
import useInterval from '../useInterval'

jest.useFakeTimers();
test('测试useInterval组件', () => {
    const active:any = jest.fn()
    const { rerender } = renderHook(() => useInterval(active,1000));
    rerender();
    jest.advanceTimersByTime(1000);
    expect(active).not.toBeCalled()
}) 
