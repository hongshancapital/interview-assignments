import React from 'react';
import { act, render } from '@testing-library/react';
import App from './App';
import usePlay from './hooks/use-play';
import { renderHook } from '@testing-library/react-hooks';

test('should have titles', () => {
  const { getByText } = render(<App />);
  const xPhone = getByText("xPhone");
  expect(xPhone).toBeInTheDocument();
  const tablet = getByText("Tablet");
  expect(tablet).toBeInTheDocument();
  const airPods = getByText("Get airPods.");
  expect(airPods).toBeInTheDocument();
});

test('should valid index when count is 0', () => {
  const { result } = renderHook(() => usePlay(0, 0));

  expect(result.current.index).toBe(0);

  act(() => {
    result.current.setCurrentStep({index: 1});
  });

  expect(result.current.index).toBe(0);
});

test('should valid index when count more than 0', () => {
  const { result } = renderHook(() => usePlay(3, 1000));

  expect(result.current.index).toBe(0);

  act(() => {
    result.current.setCurrentStep({index: 1});
  });

  expect(result.current.index).toBeLessThan(3);

  act(() => {
    result.current.setCurrentStep({index: 5});
  });

  expect(result.current.index).toBeLessThan(3);
});