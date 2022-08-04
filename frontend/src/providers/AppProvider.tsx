import React, { useReducer }from "react";
import { CarouselContext, CarouselDispatchContext, initialData, carouselReducer} from '../store/AppContext';


export function AppProvider({ children }: { children: JSX.Element }) {
  const [carousels, dispatch] = useReducer(
    carouselReducer,
    initialData
)

  return (
    <CarouselContext.Provider value={carousels}>
      <CarouselDispatchContext.Provider value={dispatch}>
        {children}
      </CarouselDispatchContext.Provider>
    </CarouselContext.Provider>
  );
}