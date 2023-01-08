export default function timoutAsync(ms: number) {
  return new Promise((resolve) => {
    setTimeout(resolve, ms)
  })
}
