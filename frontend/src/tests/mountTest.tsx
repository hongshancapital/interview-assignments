import React from 'react';
import { render } from '@testing-library/react';

// eslint-disable-next-line jest/no-export
export default function mountTest(Component: any) {
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
