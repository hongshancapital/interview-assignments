import React from 'react'
import { act } from '@testing-library/react'
import { renderHook } from '@testing-library/react-hooks'
import useIndex from './useIndex'

const sleep = (duration: number) => {
  return new Promise<void>((res) => {
    setTimeout(() => {
      res()
    }, duration)
  })
}

test('return index with empty children array', async () => {
  const { result } = renderHook(() =>
    useIndex({ children: [], duration: 3000 })
  )
  const { currIndex } = result.current
  expect(currIndex).toEqual(0)
})

test('change index to next with multiple children', async () => {
  const { result } = renderHook(() =>
    useIndex({ children: [null, null], duration: 3000 })
  )

  let index = result.current.currIndex

  await act(async () => {
    await sleep(3000)
    index = result.current.currIndex
  })

  expect(index).toEqual(1)
})
