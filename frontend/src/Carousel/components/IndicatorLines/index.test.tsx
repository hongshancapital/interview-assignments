import React from 'react';
import { render } from '@testing-library/react';
import { IndicatorLines } from './index';

test('renders IndicatorLines', () => {
    const { container } = render(<IndicatorLines active={true} duration={4000}/>);

    const itemDom = container.getElementsByClassName('indicator-lines')[0];
    const targetDom = itemDom.getElementsByClassName('cur')[0];
    const { animationDuration } = window.getComputedStyle(targetDom);
    expect(targetDom).toBeDefined();
    expect(animationDuration).toBe("4s");
});
