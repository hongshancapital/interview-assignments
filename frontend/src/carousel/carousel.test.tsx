import { render } from '@testing-library/react';
import React from 'react';
import { Carousel } from './carousel';

describe('Carousel', () => {
  it('should render children', () => {
    const { getByText } = render(
      <Carousel>
        <div>firstChild</div>
        <div>secondChild</div>
        <div>thirdChild</div>
      </Carousel>
    );

    expect(getByText("firstChild")).toBeTruthy();
    expect(getByText("secondChild")).toBeTruthy();
    expect(getByText("thirdChild")).toBeTruthy();
  });
});