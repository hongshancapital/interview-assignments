interface CalcTrackStyleProps {
  activeIndex: number;
  sliderWidth: number;
  slidersCount: number;
  isAnimating: boolean;
  speed: number;
}

export interface TrackStyle {
  width: string;
  transform: string;
  transition: string;
}

export const calcTrackStyle = ({
  activeIndex,
  sliderWidth,
  slidersCount,
  isAnimating,
  speed
}: CalcTrackStyleProps): TrackStyle => {
  let transition = '';
  if (isAnimating) {
    transition = `transform ${speed}ms ease-in-out`;
  }

  const trackWidth = sliderWidth * slidersCount;
  const translate = sliderWidth * activeIndex * -1;

  return {
    width: `${trackWidth}px`,
    transform: `translateX(${translate}px)`,
    transition
  };
};
