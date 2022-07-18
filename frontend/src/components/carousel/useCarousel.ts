import { useReducer, useEffect} from 'react';

const FRAME_PER_SECOND = 60;

interface CarouselState {
  target: number;
  current: number;
  progress: number;
}

const initialCarouselState: CarouselState = {
  target: 0,
  current: 0,
  progress: 0,
};

export interface CarouselNextAction {
  type: 'next';
  length: number;
}

export interface CarouselPrgressAction {
  type: 'progress';
  progress: number;
}

export interface CarouselDoneAction {
  type: 'done';
}


type CarouselAction =
  | CarouselNextAction
  | CarouselPrgressAction
  | CarouselDoneAction;

export function next(length: number, current: number) {
  return (current + 1) % length;
}

export function carouselReducer(state: CarouselState, action: CarouselAction): CarouselState {
  console.log("receive ", state, action)
  switch (action.type) {
     case 'next':
      return {
        ...state,
        target: next(action.length, state.current),
      };
    case 'progress': {
      return {
        ...state,
        progress: action.progress,
      }
    } 
    case 'done':
      return {
        ...state,
        progress: 0,
        current: state.target,
      };
    default:
      return state;
  }
}

export interface CarouselOptions {
  slidesPresented?: number;
}

/**
 * 
 * @param length The length of slide array
 * @param interval The time of every slide present in ms
 * @param transationTime The time of slide transation when slide to next in ms
 * @returns an array of carsouel state with [current slide index, current stay progress, next slide index]
 *
 * @note when the next slide index is not equal to current slide index, 
 *  you should do transation using the transation animation and the time of the transation should be the transationTime you passed in.
 */
export function useCarousel(
  length: number,
  interval: number,
  transationTime: number,
): [number, number, number] {

  const [state, dispatch] = useReducer(carouselReducer, initialCarouselState);
 
  useEffect(() => {
    const count = FRAME_PER_SECOND * (interval / 1000);
    let curCount = 0;
    const step = () => {
      if (count === curCount) {
        dispatch(({type: 'next', length}))
        return;
      }
      curCount++;
      dispatch({type: 'progress', progress:  (curCount / count) * 100 })
      requestAnimationFrame(step)
    }
    requestAnimationFrame( step )
  }, [ state.current, interval, length]);

  useEffect(() => {
    const id = setTimeout(() => dispatch({ type: 'done' }), transationTime);
    return () => clearTimeout(id);
  }, [state.target]);

  return [state.current, state.progress, state.target];
}