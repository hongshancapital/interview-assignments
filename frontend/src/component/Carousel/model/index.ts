import { createContext } from "react";
import { getDefaultElementSize } from "src/util"
import { IModel, IModelAction } from "./interface";
 

export const initModelValue: IModel = {
   currentIndex: 0,
   preIndex: null,
   sliderLen: 0,
   carouselSize: getDefaultElementSize(),
   containerTransform: { transform: 'translateX(0px))', transitionDuration: '0ms'}
}


export const modelContext = createContext<{state:IModel, dispatch:React.Dispatch<IModelAction>}>({state: initModelValue, dispatch:(value: IModelAction) => {}})


export const modelReducer = (state: IModel, action: IModelAction) => {
    switch (action.type) {
      case 'UPDATE_STATE': 
        return {
            ...state,
            ...action.payload
        };
      default:
        throw new Error(`不存在的 action type`);
    }
};
