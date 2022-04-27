import React, { useMemo, ReactElement, ReactNode } from 'react';

interface RepeatProps {
  numTimes: number,
  children: Function
};

function Repeat({ numTimes, children }: RepeatProps): ReactElement {
  // console.log('repeat', numTimes)
  let items: ReactNode[] = [];
  for (let i = 0; i < numTimes; i++) {
    items.push(children(i));
  }
  return <>{items}</>;
}

export default Repeat;