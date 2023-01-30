import { NextButton, PreviousButton } from './default-controls';
import {
  Alignment,
  ControlProps,
  D3EasingFunctions,
  InternalCarouselProps,
} from './types';

const defaultProps: InternalCarouselProps = {
  adaptiveHeight: true,
  afterSlide: () => {
    // do nothing
  },
  animation: 'zoom',
  loop: false,
  autoplay: true,
  autoplayInterval: 3000,
  autoplayReverse: false,
  beforeSlide: () => {
    // do nothing
  },
  cellAlign: Alignment.Left,
  cellSpacing: 0,
  defaultControlsConfig: {},
  disableAnimation: false,
  disableEdgeSwiping: false,
  easing: D3EasingFunctions.EaseCircleOut,
  edgeEasing: D3EasingFunctions.EaseElasticOut,
  frameAriaLabel: 'carousel-slider',
  renderCenterLeftControls: (props: ControlProps) => (
    <PreviousButton {...props} />
  ),
  renderCenterRightControls: (props: ControlProps) => <NextButton {...props} />,
  slideIndex: 0,
  slidesToScroll: 1,
  slidesToShow: 1,
  speed: 500,
  style: {},
  vertical: false,
  withoutControls: false,
  children: <></>,
};

export default defaultProps;
