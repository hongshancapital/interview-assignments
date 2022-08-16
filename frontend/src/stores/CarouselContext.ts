import React, { createContext, useContext, useReducer } from 'react'
import { ICarouselData, IDescStyle, ECarouselActionType, TCarouselAction } from "./types"
const LAST_ID = 2

 const DESC_MAIN: IDescStyle = {
  color: '#000',
  fontSize: '40px',
  paddingTop: '224px'
 }

 const DESC_VICE: IDescStyle = {
  color: '#000',
  fontSize: '32px',
 }

export const initialData: ICarouselData = {
  move: false,
  progressId: 0,
  lastId: LAST_ID,
  posters: [
    { 
      posterId: 0,
      bgColor:'#111', 
      pic: {
        name: 'iphone.png',
        width: '90px',
        height: '110px'
      },
      descs: [
        {
          text: 'xPhone',
          style: { ...DESC_MAIN, color: '#fff' }
        },
        {
          text: 'Lots to love. Less to spend.',
          style: { ...DESC_VICE, color: '#fff', paddingTop: '32px' }
        },
        {
          text: 'Starting at $399.',
          style: { ...DESC_VICE, color: '#fff', paddingTop: '12px' }
        },
      ]
    },
    {
      posterId: 1,
      bgColor:'#fafafa', 
      pic: {
        name: 'tablet.png',
        width: '96px',
        height: '96px'
      }, 
      descs: [
        {
          text: 'Tablet',
          style: { ...DESC_MAIN }
        },
        {
          text: 'Just the right amount of everything.',
          style: { ...DESC_VICE, paddingTop: '32px' }
        },
      ]
    },
    {
      posterId: 2,
      bgColor:'#f1f1f3', 
      pic: {
        name: 'airpods.png',
        width: '102px',
        height: '92px'
      }, 
      descs: [
        {
          text: 'Buy a Tablet or xPhone for college.',
          style: { ...DESC_MAIN }

        },
        {
          text: 'Get arPods',
          style: { ...DESC_MAIN, paddingTop: '12px' }
        },
      ]
    },
  ]    
}

export function carouselReducer(appData: ICarouselData, action: TCarouselAction) {
	switch (action.type) {
			case ECarouselActionType.SET_CURRENT: {
        const { progressId, lastId } = appData
        const current = progressId === lastId ? 0 : progressId + 1
				return {...appData, progressId: current, move: false}
			}
      case ECarouselActionType.SET_MOVE: {
        return {...appData, move: true}
      }
			default: {
			  return appData
			}
	}
}

export const CarouselContext = createContext<ICarouselData>(initialData)

export const CarouselDispatchContext = createContext<React.Dispatch<TCarouselAction>>(() => null)

export function useCarousel() {
  return useContext(CarouselContext);
}

export function useCarouselDispatch() {
  return useContext(CarouselDispatchContext);
}