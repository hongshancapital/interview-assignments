import React, { createContext, useContext, useReducer } from 'react'
import { ICarouselData, ICarouselAction, ECarouselActionType, TCarouselAction } from "./types"
const LAST_ID = 2

export const initialData: ICarouselData = {
  move: false,
  progressId: 0,
  lastId: LAST_ID,
  posters: [
    { 
      posterId: 0,
      bgColor:'#000', 
      pic:'iphone.png', 
      descs: [
        {
          color: '#fff',      
          title: true,
          text: 'xPhone',
        },
        {
          color: '#fff',
          title: false,
          text: 'Lots to love. Less to spend.',
        },
        {
          color: '#fff',
          title: false,
          text: 'Starting at $399.'
        },
      ]
    },
    {
      posterId: 1,
      bgColor:'#fff', 
		  pic:'tablet.png',  
      descs: [
        {
          color: '#000',
          title: true,
          text: 'Tablet',
        },
        {
          color: '#000',
          title: false,
          text: 'Just the right amount of everything.',
        },
      ]
    },
    {
      posterId: 2,
      bgColor:'#fff', 
		  pic:'airpods.png',
      descs: [
        {
          color: '#000',
          title: true,
          text: 'Buy a Tablet or xPhone for college.',
        },
        {
          color: '#000',
          title: true,
          text: 'Get arPods',
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