import { ICarouselData, IDescStyle } from "./types"

export const CONTENT_STYLE: IDescStyle = {
  color: '#000',
  fontSize: '40px',
  paddingTop: '224px'
}

const CONTENT_STYLE_OTHER: IDescStyle = {
  color: '#000',
  fontSize: '32px',
}
/**
 * 模拟potsers数据
 */
export const initData: ICarouselData = {
  autoPlay: true,
  progressId: 0,
  posters: [
    {
      posterId: 0,
      bgColor: '#111',
      pic: {
        name: 'iphone.png',
        width: '90px',
        height: '110px'
      },
      contents: [
        {
          text: 'xPhone',
          style: { ...CONTENT_STYLE, color: '#fff' }
        },
        {
          text: 'Lots to love. Less to spend.',
          style: { ...CONTENT_STYLE_OTHER, color: '#fff', paddingTop: '32px' }
        },
        {
          text: 'Starting at $399.',
          style: { ...CONTENT_STYLE_OTHER, color: '#fff', paddingTop: '12px' }
        },
      ]
    },
    {
      posterId: 1,
      bgColor: '#fafafa',
      pic: {
        name: 'tablet.png',
        width: '96px',
        height: '96px'
      },
      contents: [
        {
          text: 'Tablet',
          style: { ...CONTENT_STYLE }
        },
        {
          text: 'Just the right amount of everything.',
          style: { ...CONTENT_STYLE_OTHER, paddingTop: '32px' }
        },
      ]
    },
    {
      posterId: 2,
      bgColor: '#f1f1f3',
      pic: {
        name: 'airpods.png',
        width: '102px',
        height: '92px'
      },
      contents: [
        {
          text: 'Buy a Tablet or xPhone for college.',
          style: { ...CONTENT_STYLE }

        },
        {
          text: 'Get arPods',
          style: { ...CONTENT_STYLE, paddingTop: '12px' }
        },
      ]
    },
  ]
}