import React from 'react';
import { render } from '@testing-library/react';

export function mountTest(Component: React.ComponentType) {
  describe(`mount and unmount`, () => {
    it(`component could be updated and unmounted without errors`, () => {
      const { unmount, rerender } = render(<Component />);
      expect(() => {
        rerender(<Component />);
        unmount();
      }).not.toThrow();
    });
  });
}
