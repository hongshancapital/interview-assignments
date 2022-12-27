export function scrollVelocity(
  target: HTMLElement,
  distance: number,
  velocity: number = 10
) {
  let timeId: unknown = null
  let value = velocity || 10
  const direction = target.scrollLeft < distance
  const num = direction
    ? (distance - target.scrollLeft) / velocity
    : target.scrollLeft / velocity
  timeId = setInterval(() => {
    value--
    target.scrollLeft = direction
      ? target.scrollLeft + num
      : target.scrollLeft - num
    if (value <= 0) {
      target.scrollLeft = direction ? distance : 0
      clearInterval(timeId as number)
    }
  }, 30)
}

export function getImgColor(img: HTMLImageElement) {
  const w = img.width
  const h = img.height
  const canvas = document.createElement('canvas')
  canvas.width = w
  canvas.height = h
  const ctx = canvas.getContext('2d')

  ctx!.drawImage(img, 0, 0, w, h)

  const data = ctx!.getImageData(0, 0, w, h).data

  let r = 1,
    g = 1,
    b = 1
  for (let row = 0; row < h; row++) {
    for (let col = 0; col < w; col++) {
      if (row === 0) {
        r += data![w * row + col]
        g += data![w * row + col + 1]
        b += data![w * row + col + 2]
      } else {
        r += data![(w * row + col) * 4]
        g += data![(w * row + col) * 4 + 1]
        b += data![(w * row + col) * 4 + 2]
      }
    }
  }

  r /= w * h
  g /= w * h
  b /= w * h

  function roundNumber(num: number) {
    return Math.round(num)
  }

  const num =
    roundNumber(r) * 0.299 + roundNumber(g) * 0.587 + roundNumber(b) * 0.114

  return num >= 192
}
