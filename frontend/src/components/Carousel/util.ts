export function getTransform(translate: number, speed = 0) {
  return {
    transform: `translateX(${-translate}px)`,
    transitionDuration: `${speed}ms`,
  };
}
