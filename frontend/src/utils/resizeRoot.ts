// @ts-nocheck
export default (num?: number) => {
  let numSize = num || 7.2
  let uAgent = window.navigator.userAgent
  let isIOS = uAgent.match(/iphone/i)
  let isYIXIN = uAgent.match(/yixin/i)
  let is2345 = uAgent.match(/Mb2345/i)
  let ishaosou = uAgent.match(/mso_app/i)
  let isSogou = uAgent.match(/sogoumobilebrowser/gi)
  let isLiebao = uAgent.match(/liebaofast/i)
  let isGnbr = uAgent.match(/GNBR/i)
  let wWidth =
      window.screen.width > 0
        ? window.innerWidth >= window.screen.width || window.innerWidth === 0
          ? window.screen.width
          : window.innerWidth
        : window.innerWidth,
    wDpr: string,
    wFsize
  let wHeight =
    window.screen.height > 0
      ? window.innerHeight >= window.screen.height || window.innerHeight === 0
        ? window.screen.height
        : window.innerHeight
      : window.innerHeight
  if (window.devicePixelRatio) {
    wDpr = String(window.devicePixelRatio)
  } else {
    wDpr = String(isIOS ? (wWidth > 818 ? 3 : wWidth > 480 ? 2 : 1) : 1)
  }
  if (isIOS) {
    wWidth = window.screen.width
    wHeight = window.screen.height
  }
  if (wWidth > wHeight) {
    wWidth = wHeight
  }
  wWidth = wWidth > 640 ? 640 : wWidth
  wFsize = wWidth / numSize
  // wFsize = wWidth > 1080 ? 144 : wWidth / numSize
  // wFsize = wFsize > 32 ? wFsize : 32
  window.screenWidth_ = wWidth
  if (isYIXIN || is2345 || ishaosou || isSogou || isLiebao || isGnbr) {
    //YIXIN 和 2345 这里有个刚调用系统浏览器时候的bug，需要一点延迟来获取
    setTimeout(function () {
      wWidth =
        window.screen.width > 0
          ? window.innerWidth >= window.screen.width || window.innerWidth === 0
            ? window.screen.width
            : window.innerWidth
          : window.innerWidth
      wHeight =
        window.screen.height > 0
          ? window.innerHeight >= window.screen.height ||
            window.innerHeight === 0
            ? window.screen.height
            : window.innerHeight
          : window.innerHeight
      // wFsize = wWidth > 1080 ? 144 : wWidth / numSize
      // wFsize = wFsize > 32 ? wFsize : 32
      wFsize = wWidth / numSize
      document.getElementsByTagName('html')[0].dataset.dpr = wDpr
      document.getElementsByTagName('html')[0].style.fontSize = wFsize + 'px'
      window.resetFontSize(wFsize)
    }, 500)
  } else {
    document.getElementsByTagName('html')[0].dataset.dpr = wDpr
    document.getElementsByTagName('html')[0].style.fontSize = wFsize + 'px'
    window.resetFontSize(wFsize)
  }
}

window.resetFontSize = (wFsize: number) => {
  let originRootFontSize = Math.floor(
    parseFloat(
      window
        .getComputedStyle(document.documentElement, null)
        .getPropertyValue('font-size')
    )
  )
  let realSize = Math.floor(wFsize)
  if (originRootFontSize === realSize) {
    return
  }
  // 计算原字体和放大后字体的比例
  let scaleFactor = realSize / originRootFontSize

  // 取html元素的字体大小
  // 注意，这个大小也经过缩放了
  // 所以下方计算的时候 *scaledFontSize是原来的html字体大小
  // 再次 *scaledFontSize才是我们要设置的大小
  if (scaleFactor !== 1) {
    document.getElementsByTagName('html')[0].style.cssText =
      'font-size:' +
      originRootFontSize * scaleFactor * scaleFactor +
      'px !important'
  }
}
