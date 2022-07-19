import { render } from "@testing-library/react";
import "@testing-library/jest-dom";
import React from "react";
import Dot from "../Dot";

describe('测试Dot组件', () => {
  it('组件非选中状态，进度条没有class和style', () => {
    const { container } = render(<Dot isActive={false} duration={0} />);
    const dot = container.getElementsByClassName('carousel-dot')[0];
    expect(dot).toBeInTheDocument();
    expect(dot.children.length).toBe(1);
    const progress = dot.children[0];
    expect(progress.getAttribute('class')).toBeNull();
    expect(progress.getAttribute('style')).toBeNull();
  });

  it('组件选中状态，进度条有class和style', () => {
    const duration = 1000;
    const { container } = render(<Dot isActive={true} duration={duration} />);
    const dot = container.getElementsByClassName('carousel-dot')[0];
    expect(dot).toBeInTheDocument();
    expect(dot.children.length).toBe(1);
    const progress = dot.children[0];
    expect(progress.getAttribute('class')).toBe('carousel-dot-active');
    expect(progress.getAttribute('style')).toContain(`animation-duration: ${duration}ms`);
  });
});
