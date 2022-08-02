export function prepareStyles(
  slidesCount: number,
  pauseTime: number,
  transTime: number
): string {
  const totalTime = slidesCount * (pauseTime + transTime); // e.g. 9
  const pausingTime = pauseTime / totalTime; // e.g. 0.27999...
  // const transitionTime = transTime / totalTime; // e.g. 0.05333...
  let keyframesOfSliding = '@keyframes sliding {\n';
  for (let i = 0; i < slidesCount; i++) {
    keyframesOfSliding += `${((1 / slidesCount) * i * 100).toFixed(2)}%, ${(
      ((1 / slidesCount) * i + pausingTime) *
      100
    ).toFixed(2)}% { transform: translateX(-${100 * i}vw) }\n`;
  }
  keyframesOfSliding += '100% { transform: translateX(0); }\n';
  keyframesOfSliding += '}';

  let controlBarProgress = '';
  for (let i = 0; i < slidesCount; i++) {
    controlBarProgress += `.control .bar:nth-of-type(${i + 1}) .progress {
      animation-delay: ${(pauseTime + transTime) * i - transTime}s;
    }`;
  }

  const full: string = ((1 / slidesCount) * 100).toFixed(2); // e.g. '33.33'
  const fullMore: string = ((1 / slidesCount) * 100 + 0.01).toFixed(2); // e.g. '33.34'
  const start: string = (
    (transTime / slidesCount / (pauseTime + transTime)) *
    100
  ).toFixed(2); // e.g. '5.33'

  return `
  ${keyframesOfSliding}
  .control .bar .progress {
    animation-duration: ${totalTime}s;
  }
  ${controlBarProgress}
  @keyframes progress {
    0%,
    ${fullMore}%,
    100% {
      transform: scaleX(0);
    }
    ${start}% {
      transform: scaleX(14);
    }
    ${full}% {
      transform: scaleX(80);
    }
  }`;
}
