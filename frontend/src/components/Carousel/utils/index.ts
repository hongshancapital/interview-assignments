interface CalcTrackStyleProps {
  activeIndex: number;
  slidersCount: number;
  isAnimating: boolean;
  speed: number;
}

export interface TrackStyle {
  transform: string;
  transition: string;
}

export const calcTrackStyle = ({
  activeIndex,
  slidersCount,
  isAnimating,
  speed
}: CalcTrackStyleProps): TrackStyle => {
  let transition = '';
  if (isAnimating) {
    transition = `transform ${speed}ms ease-in-out`;
  }

  const translate = (activeIndex * -1 / slidersCount) * 100;

  return {
    transform: `translateX(${translate}%)`,
    transition
  };
};
