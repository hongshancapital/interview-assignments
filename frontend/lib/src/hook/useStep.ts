import { useEffect, useRef, useState } from 'react'

interface IStepOptions {
  duration: number
  lastStep: number
}

function useStep(options: IStepOptions) {
  const [step, setStep] = useState(0)
  const timer = useRef<ReturnType<typeof setTimeout> | null>()

  function nextStep() {
    if (timer.current)
      clearTimeout(timer.current)
    timer.current = setTimeout(() => {
      let _step = step
      if (step === options.lastStep)
        _step = -1
      _step = _step + 1
      setStep(_step)
    }, options.duration)
  }

  useEffect(() => {
    nextStep()

    return () => {
      if (timer.current)
        clearTimeout(timer.current)
    }
  }, [step])

  return [step, nextStep] as const
}

export default useStep
