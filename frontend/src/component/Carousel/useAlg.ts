import { AbstractAlg, AlgOptions, AlgMode } from "./AbstractAlg"
import { useMemo, useState } from "react"
import { InfiniteLoopAlg } from "./Algs"
import { OneSideAlg } from "./Algs"

/**
 * 和UI对接的钩子 
 * @param algOptions 
 * @returns 
 */
 export function useAlg(algOptions : AlgOptions & {mode : AlgMode}) {
  const alg : AbstractAlg = useMemo(() => {
    switch(algOptions.mode) {
      case "loop":
        return new InfiniteLoopAlg(algOptions)
      case "one-side":
        return new OneSideAlg(algOptions)
      default:
        throw new Error(`unkown alg type:${algOptions.mode}`)
    }
  }, [])

  const [viewport, setVP] = useState(alg.viewport())
  return {
    getCurrent : () => alg.getCurrent(),
    playIndex : () => alg.getPlayIndex(),
    next : () => {
      alg.next()
      setVP(alg.viewport())
    },
    viewport ,
    styles : alg.transitionStyle()
  }

}