import React from "react";
import renderer from "react-test-renderer";
import "jest-styled-components";
import { Item } from "../style";

describe('IndicatorItem style', () => {
  test('Item', () => {
    const container = renderer.create(<Item active={true} duration={100} />).toJSON();

    expect(container).toHaveStyleRule('display', 'inline-block');
    expect(container).toHaveStyleRule('position', 'relative');
    expect(container).toHaveStyleRule('width', '50px');
  });
});