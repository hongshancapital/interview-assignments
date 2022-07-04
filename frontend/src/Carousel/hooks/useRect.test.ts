/*
 * @Author: danjp
 * @Date: 2022/6/25 13:16
 * @LastEditTime: 2022/6/25 13:16
 * @LastEditors: danjp
 * @Description:
 */
import React from 'react';
import { renderHook } from '@testing-library/react-hooks';
import useRect from './useRect';

describe('useRect', () => {
  it('should be defined', () => {
    expect(useRect).toBeDefined();
  });
  
  it('should with document.body', () => {
    jest.spyOn(React, 'useRef').mockReturnValue({
      current: document.body,
    });
    
    const { result } = renderHook(() => useRect());
    expect(result.current.itemWidth).toEqual(document.body.offsetWidth);
  });
  
  
  let div: HTMLDivElement;
  beforeEach(() => {
    div = document.createElement('div');
    div.style.width = '1024px';
    document.body.append(div);
  });
  
  afterEach(() => {
    document.body.removeChild(div);
  });
  
  it('should any element width', () => {
    jest.spyOn(React, 'useRef').mockReturnValue({
      current: div!,
    });
  
    const { result } = renderHook(() => useRect());
    expect(result.current.itemWidth).toEqual(div!.offsetWidth);
  });
});
