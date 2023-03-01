import iPhone from '../assets/image/iphone.png'
import tablet from '../assets/image/tablet.png'
import airpods from '../assets/image/airpods.png'

export interface Banner {
  /**
   * 标题
   */
  title: string
  /**
   * 描述
   */
  desc: string
  /**
   * 图片地址
   */
  url: string
  /**
   * 是否暗色文字
   */
  isDark: boolean
}

export function getBannerList(): Banner[] {
  return [
    { title: 'xPhone', desc: 'Lots to love. Less to spend.\r\nStarting at $399.', url: iPhone, isDark: false },
    { title: 'Tablet', desc: 'Just the right amount of everything.', url: tablet, isDark: true },
    { title: 'Buy a Tablet or xPhone for college.\r\nGet airpods.', desc: '', url: airpods, isDark: true },
  ]
}
