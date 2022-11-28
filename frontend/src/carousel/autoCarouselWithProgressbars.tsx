import React, { ReactNode, Children, useState } from 'react';
import { Carousel } from './carousel'
import { Progressbars } from './progressbars'

function AutoCarouselWithProgressbars(props: {
  children: ReactNode | ReactNode[],
  timeout: number,
  progressDataTestId?: string
}) {
  const { children, timeout, progressDataTestId, ...attrs } = props
  const length = Children.count(children)
  const [active, setActive] = useState(0);
  return (
    <>
      <Carousel
        active={active}
        {...attrs}>
        {children}
      </Carousel>
      <Progressbars
        data-testid={progressDataTestId}
        length={length}
        active={active}
        timeout={timeout}
        onTimeout={() => setActive((active + 1) % length)}
        onClick={(i) => setActive(i)}
      />
    </>
  );
}

export { AutoCarouselWithProgressbars };
