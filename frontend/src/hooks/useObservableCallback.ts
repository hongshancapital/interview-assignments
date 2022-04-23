import { Observable } from 'rxjs'
import { useRef, useEffect, useLayoutEffect } from 'react'

export function useObservableCallback<T>(
  input$: Observable<T>,
  callback: (state: T) => void
) {
    const observableRef = useRef(input$)
    const cbRef = useRef(callback)

    useLayoutEffect(() => {
        observableRef.current = input$
        cbRef.current = callback
    })
    useEffect(() => {
        const currentInput$ = observableRef.current
        const subscription = currentInput$.subscribe(
            (value) => {
                if (input$ !== observableRef.current) {
                    return
                }
                cbRef.current(value)
            }
        )

        return () => {
            subscription.unsubscribe()
        }
    }, [input$])
}