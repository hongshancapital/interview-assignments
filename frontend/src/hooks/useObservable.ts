import { Observable } from 'rxjs'
import { useDebugValue, useState } from 'react'
import { useObservableCallback } from './useObservableCallback'

export function useObservable<T>(
  input$: Observable<T>,
  initialState: T
): T {
    const [state, setState] = useState(initialState)
    useObservableCallback(input$, setState)
    useDebugValue(state)

    return state
}