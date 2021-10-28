export class AsyncUtil {

  static wait(time : number) : Promise<void> {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve()
      }, time)
    })
  }

  static waitRaf(n : number = 0) {
    return new Promise(resolve => {
      function loop() {
        requestAnimationFrame(() => {
          if(n -- === 0) {
            resolve(null)
            return
          }
          loop()
        })
      }
      loop()
      
    })
  }

  
}