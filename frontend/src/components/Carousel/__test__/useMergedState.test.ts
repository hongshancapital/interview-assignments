import { renderHook } from '@testing-library/react-hooks'
import {act} from "react-dom/test-utils";
import useMergedState from '../hooks/useMergedState';
import useTrack from '../hooks/useTrack';

describe('useMergedState', ()=> {
    it("测试非受控状态", ()=>{
        const { result } = renderHook(() => useMergedState<number>(1, {}));
        expect(result.current[0]).toBe(1);
        act(()=>{
            result.current[1](2);
        })
        expect(result.current[0]).toBe(2);
    })
    it("测试受控状态", ()=>{
        const { result } = renderHook(() => useMergedState<number>(0, {value: 1}));
        expect(result.current[0]).toBe(1);
        act(()=>{
            result.current[1](3);
        })
        expect(result.current[0]).toBe(3);
    })
    it("测试默认值", ()=>{
        const { result } = renderHook(() => useMergedState<number>(0, {defaultValue: 2}));
        expect(result.current[0]).toBe(2);
        act(()=>{
            result.current[1](3);
        })
        expect(result.current[0]).toBe(3);
    })
})
