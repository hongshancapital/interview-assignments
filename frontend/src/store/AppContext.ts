import React, { createContext, useContext, useReducer } from 'react'
import { ICarouselData, IDescStyle, ECarouselActionType, TCarouselAction } from "./types"
const LAST_ID = 2

 const DESC_MAIN: IDescStyle = {
  color: '#000',
  fontSize: '40px',
  marginTop: '224px',
  marginBottom: '32px'
 }

 const DESC_VICE: IDescStyle = {
  color: '#000',
  fontSize: '32px',
  marginTop: '12px',
  marginBottom: '0px'
 }

export const initialData: ICarouselData = {
  move: false,
  progressId: 0,
  lastId: LAST_ID,
  posters: [
    { 
      posterId: 0,
      bgColor:'#101010', 
      pic:'iphone.png', 
      descs: [
        {
          text: 'xPhone',
          style: { ...DESC_MAIN, color: '#fff' }
        },
        {
          text: 'Lots to love. Less to spend.',
          style: { ...DESC_VICE, color: '#fff' }
        },
        {
          text: 'Starting at $399.',
          style: { ...DESC_VICE, color: '#fff' }
        },
      ]
    },
    {
      posterId: 1,
      bgColor:'#fafafa', 
		  pic:'tablet.png',  
      descs: [
        {
          text: 'Tablet',
          style: DESC_MAIN
        },
        {
          text: 'Just the right amount of everything.',
          style: { ...DESC_VICE, marginTop: '0px' }
        },
      ]
    },
    {
      posterId: 2,
      bgColor:'#f2f2f4', 
		  pic:'airpods.png',
      descs: [
        {
          text: 'Buy a Tablet or xPhone for college.',
          style: { ...DESC_MAIN, marginBottom: '0px' }

        },
        {
          text: 'Get arPods',
          style: { ...DESC_MAIN, marginTop: '12px' }
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