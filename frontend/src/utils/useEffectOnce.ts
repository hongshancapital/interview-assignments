import { EffectCallback, useEffect } from "react";

const useEffectOnce = (effect: EffectCallback) => {
  useEffect(effect, []) // eslint-disable-line react-hooks/exhaustive-deps
}

export default useEffectOnce;
