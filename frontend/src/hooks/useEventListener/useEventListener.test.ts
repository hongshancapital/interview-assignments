import { fireEvent } from '@testing-library/react'
import { renderHook } from '@testing-library/react-hooks/native'

import useEventListener from './useEventListener'


describe('useEventListener()', () => {
  it('should call the event listener handler when the event is triggered', () => {
    const eventName = 'click'
    const handler = jest.fn()
    const ref = { current: document.createElement('div') };
    renderHook(() => useEventListener(eventName, handler, ref))

    fireEvent.click(ref.current)
		jest.useFakeTimers();

    expect(handler).toHaveBeenCalledTimes(1);
  })
})