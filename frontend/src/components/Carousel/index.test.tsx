import { classNames, throttle } from './index'

test('classNames function is correct', () => {
  expect(classNames(false && 'abc')).toBe('')
  expect(classNames(true && 'abc')).toBe('abc')
  expect(classNames('abc', 'def')).toBe('abc def')
  expect(classNames('abc', false && 'def')).toBe('abc')
})
test('throttle function is correct', () => {
  let times = 0
  function add() {
    times++
  }
  const warapAdd = throttle(add, 500)
  warapAdd()
  const timer = setInterval(() => {
    warapAdd()
  }, 100)
  setTimeout(() => {
    clearInterval(timer)
    expect(times).toBeLessThan(2000 / 100)
  }, 2000)
})
