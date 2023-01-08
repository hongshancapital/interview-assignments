import { Mutex } from 'async-mutex'
import * as base62 from '../utils/base62'
import { collections } from '../components/database'
import { KeyGenerator } from './HashKeyGenerator'
interface Config {
  stepSize: number
}
export default class implements KeyGenerator {
  signalValue = -1
  currStep = 0
  mutex = new Mutex()
  constructor(public config: Config) {}
  getNextSignalValue = async () => {
    const signal = await collections.Signal.findOneAndUpdate(
      { name: 'shorturl' },
      { $inc: { value: this.config.stepSize } },
      { upsert: true, returnDocument: 'after' },
    )
    // eslint-disable-next-line @typescript-eslint/no-non-null-assertion
    return signal.value!.value - this.config.stepSize
  }
  generateKey = async (keyLength: number) => {
    if (this.signalValue == -1 || this.currStep >= this.config.stepSize) {
      const release = await this.mutex.acquire()
      try {
        if (this.signalValue == -1 || this.currStep >= this.config.stepSize) {
          this.signalValue = await this.getNextSignalValue()
          this.currStep = 0
        }
      } finally {
        release()
      }
    }
    this.currStep += 1
    const seq = this.signalValue + this.currStep
    const shortUrlKey = base62.encode(seq)
    if (shortUrlKey.length > keyLength) {
      throw new Error(`key length exceed ${keyLength}`)
    }
    return shortUrlKey
  }
}
