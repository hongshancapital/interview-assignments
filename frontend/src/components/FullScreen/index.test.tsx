import { render } from '@testing-library/react';
import React from 'react';
import { FullScreen } from './index';

test('render full screen component', () => {
  const { getByTestId, container, rerender } = render(
    <FullScreen>
      <div data-testid="child">Hello World</div>
    </FullScreen>
  );
  expect(getByTestId('child')).toBeInTheDocument();
  expect(getByTestId('child').textContent).toBe('Hello World');
  const compDom = container.firstChild as HTMLElement;
  expect(compDom).not.toBeNull();
  expect(compDom).toHaveClass('full');
  expect(compDom).toHaveStyle(`width: ${window.innerWidth}px; height: ${window.innerHeight}px`);
  rerender(<FullScreen center>Hello</FullScreen>);
  expect(compDom).toHaveClass('center');
  rerender(<FullScreen className="bbb">Hello</FullScreen>);
  expect(compDom).toHaveClass('bbb');
  rerender(<FullScreen style={{ backgroundColor: 'red' }}>Hello</FullScreen>);
  expect(compDom).toHaveStyle('background-color: red');
  expect(container).toMatchSnapshot();
});
