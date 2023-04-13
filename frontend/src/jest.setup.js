import 'web-animations-js';

Object.defineProperty(HTMLElement.prototype, 'animate', {
  value: function () {
    return {
      play: () => {},
      pause: () => {},
      currentTime: 0,
    };
  },
});
