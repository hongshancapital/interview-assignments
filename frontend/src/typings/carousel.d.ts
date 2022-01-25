// the carousel props
interface CarouselProps {
  // switchInterval: 3000,  // the carousel switching interval (unit: ms)
  switchInterval?: number;
  // whether pause switching when mouse is hover (default: true)
  pauseOnHover?: boolean;
  // whether lazy render slices (default: true)
  lazyRenderEnabled?: boolean;
  // the class name of the carousel (use to overwrite default styles)
  className?: string;
  // the carousel slices
  children?: JSX.Element | JSX.Element[];
}

// the carousel indicator props
interface CarouselIndicatorProps extends CarouselProps {
  // whether the indicator is actived
  actived: boolean;
}

// the carousel slice props
interface CarouselSliceProps {
  // whether lazy render the slice
  lazy?: boolean;
  // the carousel slice
  children: JSX.Element | JSX.Element[];
}

// the carousel switching state info
interface CarouselSwitchingState {
  // the index of the current carousel slice
  activeIndex?: number;
  // the timer
  timerRef: React.MutableRefObject<Timer>;
  // switch to the specific slice
  switchTo: (sliceIndex: number | undefined) => void;
  // switch to the next slice
  switchNext: () => void;
  // switch to the previous slice
  switchPrevious: () => void;
}

// the carousel switching pause state info
interface CarouselPauseState {
  // whether the carousel is paused
  paused: boolean;
  // pause the carousel switching
  pause: () => void;
  // resume the carousel switching
  resume: () => void;
}

// the carousel state info
interface CarouselState extends CarouselSwitchingState, CarouselPauseState {
}
