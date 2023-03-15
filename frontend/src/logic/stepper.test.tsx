import { CarouselStepper, useStepper } from "./stepper";
import { render } from '@testing-library/react';

describe('stepper', () => {
  test('iterate stepper', () => {
    const it = CarouselStepper(2);
    expect(it.next().value).toBe(0);
    expect(it.next().value).toBe(1);
    expect(it.next().value).toBe(0);
    expect(it.next().value).toBe(1);
  });

  test('useStepper is stable', () => {
    let next;
    function TestComp1() {
      next = useStepper(3);
      return null;
    }
    
    const ret = render(<TestComp1 />);
    const copy = next;
    ret.rerender(<TestComp1 />)
    expect(next).toEqual(copy);
  });
  test('useStepper is cycle count', () => {
    function TestComp1() {
      const next = useStepper(3);
      return next();
    }
    
    const ret = render(<TestComp1 />);
    expect(ret.container.textContent).toBe('0');
    ret.rerender(<TestComp1 />);
    expect(ret.container.textContent).toBe('1');
    ret.rerender(<TestComp1 />);
    expect(ret.container.textContent).toBe('2');
    ret.rerender(<TestComp1 />);
    expect(ret.container.textContent).toBe('0');
  });
})