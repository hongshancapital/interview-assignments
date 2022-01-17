import React from "react";
import { render, fireEvent } from "@testing-library/react";
import IndicatorItem from "../index";

describe('SlideItem', () => {
  const fn = jest.fn();
  test('render', () => {
    const container = render(<IndicatorItem active={true} duration={100} onClick={fn} />).container;
    fireEvent.click(container.getElementsByClassName('indicator')[0]);
    expect(fn).toHaveBeenCalled();
  });
})
