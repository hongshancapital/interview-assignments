import React from 'react';

import { ItemContainer, ItemContainerProps } from './styles';

interface ItemOwnProps {
  children: React.ReactNode;
  onTransitionEnd?: () => void;
}

interface ItemProps extends ItemOwnProps, ItemContainerProps {}

export const Item = (props: ItemProps) => {
  const { children, state, duration, onTransitionEnd } = props;

  const handleTransitionEnd = () => {
    if (state === 'current' && onTransitionEnd) {
      onTransitionEnd();
    }
  };

  return (
    <ItemContainer
      data-testid="item"
      state={state}
      duration={duration}
      onTransitionEnd={handleTransitionEnd}
    >
      {children}
    </ItemContainer>
  );
};
