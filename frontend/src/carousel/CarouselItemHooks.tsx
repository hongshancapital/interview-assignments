import React from 'react';

export const CarouselItemHooks: React.FC = (props) => {
  const { children } = props;
  return <div className="CarouselItem">{children}</div>;
};
