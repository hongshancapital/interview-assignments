export {}
import { KeyframeEffect, Animation } from '../tests/types'
declare global {
  interface Window {
    KeyframeEffect: any
    Animation: any
  }
}