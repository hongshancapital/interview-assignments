import React from 'react';
import { renderHook } from '@testing-library/react-hooks';
import { render, screen, waitFor } from "@testing-library/react";

import useRect from '../useRect'

function mockRect(top: number, left: number, width: number, height: number) {
  const windowWidth = 1920;
  const windowHeight = 1080;
  const right = windowWidth - left - width;
  const bottom = windowHeight - top - height;
  const rect = {
    bottom,
    height,
    left,
    right,
    top,
    width,
    x: left,
    y: top
  };
  return rect;
}


const setUp = () => renderHook(()=> {
  useRect()
})

describe('useRect', () => {
  it('should return undefined on initial render', () => {
    const {result} = setUp()
    expect(result.current).toBeUndefined();
  })
  
  it('should return rect width and height', async ()=> {
    const rect = mockRect(10, 20, 425, 40);
    const mockRes = jest
      .spyOn(Element.prototype, 'getBoundingClientRect')
      .mockReturnValue({ ...rect, toJSON: () => rect });
  
    const Component = () => {
      const {container, size} = useRect()
      return <div ref={container}>{size.width}</div>
    }
    render(<Component />);
    await waitFor(() => {
      expect(screen.getByText(rect.width)).toBeDefined();
    });
  
    mockRes.mockRestore()
  })
})

