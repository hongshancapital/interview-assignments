import { DragEvent, MouseEvent, TouchEvent, useState } from 'react'

interface Props {
  onStart?: () => void
  onMove?: (distance: number) => void
  onEnd?: (distance: number) => void
}

export default function useGestureEvents({ onStart, onMove, onEnd }: Props) {
  const [touchStartPosition, setTouchStartPosition] = useState(0)
  const [touchEndPosition, setTouchEndPosition] = useState(0)
  const [touchActive, setTouchActive] = useState(false)

  const touchStart = (event: TouchEvent | MouseEvent) => {
    setTouchActive(true)
    setTouchStartPosition(getPosition(event))
    setTouchEndPosition(getPosition(event))
    onStart && onStart()
  }

  const touchMove = (event: TouchEvent | MouseEvent) => {
    if (!touchActive) return
    const position = getPosition(event)
    setTouchEndPosition(position)
    onMove && onMove(position - touchStartPosition)
  }

  const touchEnd = (event: TouchEvent | MouseEvent) => {
    if (!touchActive) return
    onEnd && onEnd(touchEndPosition - touchStartPosition)
    setTouchStartPosition(0)
    setTouchActive(false)
  }

  const dragstart = (event: DragEvent) =>
    event.preventDefault()

  const getPosition = (event: TouchEvent | MouseEvent) =>
    'touches' in event ? event.touches[0].clientX : event.clientX

  return {
    onMouseDown: touchStart,
    onTouchStart: touchStart,
    onMouseOut: touchEnd,
    onTouchEnd: touchEnd,
    onMouseUp: touchEnd,
    onMouseMove: touchMove,
    onTouchMove: touchMove,
    onDragStart: dragstart,
  }
}