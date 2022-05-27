import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

describe('render success', () => {
  it("组件成功挂载", () => {
    const { rerender } = render(<App/>);
    expect(() => {
      rerender(<App/>)
    }).not.toThrow()
  })
});
