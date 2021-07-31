// the timer option
interface TimerOption {
  // the tick interval (ms)
  interval: number;
  // whether start tick when the timer is created
  autoStart?: boolean;
  // the callback when timer tick
  onTick?: () => void;
}
