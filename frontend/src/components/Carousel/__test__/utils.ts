import { render as ORender, RenderOptions } from '@testing-library/react';

export const render = (ui: React.ReactElement, options?: RenderOptions) => {
  const wrapper = {
    ...ORender(ui, {
      // container: document.body,
      ...options,
    }),
  } as ReturnType<typeof ORender> & {
    find: <E extends HTMLElement | SVGElement>(selector: string) => NodeListOf<E>;
    querySelector: <T extends HTMLElement | SVGElement>(selector: string) => T | null;
  };

  wrapper.find = (selector) => {
    return document.querySelectorAll(selector);
  };
  wrapper.querySelector= (selector) => {
    return document.querySelector(selector);
  };


  return wrapper;
};