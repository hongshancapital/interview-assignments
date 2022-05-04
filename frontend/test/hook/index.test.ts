import { describe, expect, it, vi } from 'vitest'
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import React from 'react'
import useStep from '@lib/hook/useStep'
import { act, renderHook } from '../utils/test-utils'

describe('useStep test', () => {
  beforeAll(() => {
    vi.useFakeTimers()
  })

  afterAll(() => {
    vi.useRealTimers()
  })

  it('3000ms works', () => {
    const duration = 3000
    const lastStep = 3
    const { result } = renderHook(() => useStep({ duration, lastStep }))
    expect(result.current[0]).toBe(0)
    act(() => {
      vi.advanceTimersToNextTimer()
    })
    expect(result.current[0]).toBe(1)
    act(() => {
      vi.advanceTimersToNextTimer()
    })
    expect(result.current[0]).toBe(2)
  })
})
