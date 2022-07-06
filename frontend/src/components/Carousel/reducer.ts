import { ICarouselState, ICarouselAction } from "./interface";

export default function carouselReducer(
  state: ICarouselState,
  action: ICarouselAction
): ICarouselState {
  switch (action.type) {
    case "prev":
      return {
        ...state,
        current: state.current - 1 < 0 ? state.count - 1 : state.current - 1,
      };
    case "next":
      return {
        ...state,
        current: state.current + 1 > state.count - 1 ? 0 : state.current + 1,
      };
    case "to":
      const to = action.payload!.current as number;
      let fixTo = to;
      if (to < 0) {
        fixTo = 0;
      } else if (to > state.count - 1) {
        fixTo = state.count - 1;
      }
      if (fixTo === state.current) {
        return state;
      }
      return {
        ...state,
        ...action.payload,
      };
    case "setCount":
    case "resize":
      return {
        ...state,
        ...action.payload,
      };
    default:
      return state;
  }
}
