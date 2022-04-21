import React from 'react';

import { indexToState } from './helper';
import { Item } from './Item';
import { IndicatorContainer, Indicator } from './Indicator';
import { useCarousel } from './useCarousel';
import { CarouselRoot, CarouselContainer } from './styles';

interface CarouselProps {
  children: React.ReactNode;
  duration?: number;
  transitionDuration?: number;
}

export const Carousel = ({
  duration = 3000,
  transitionDuration = 600,
  children,
}: CarouselProps): JSX.Element => {
  const { currentIndex, transitionEndIndex, setTransitionEndIndex, jumpTo } =
    useCarousel(React.Children.count(children), duration);

  const handleJumpTo = (index: number) => {
    jumpTo(index);
  };

  return (
    <CarouselRoot>
      <CarouselContainer>
        {React.Children.map(children, (child, index) => (
          <Item
            key={index}
            duration={transitionDuration}
            state={indexToState(index, currentIndex)}
            onTransitionEnd={() => setTransitionEndIndex(index)}
          >
            {child}
          </Item>
        ))}
      </CarouselContainer>
      <IndicatorContainer>
        {React.Children.map(children, (_, index) => (
          <Indicator
            key={index}
            index={index}
            state={indexToState(index, transitionEndIndex)}
            duration={duration}
            onClick={handleJumpTo}
          />
        ))}
      </IndicatorContainer>
    </CarouselRoot>
  );
};
