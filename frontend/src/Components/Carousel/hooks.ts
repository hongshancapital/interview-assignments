import { useEffect, useState } from 'react';
import { dotPluginFactory } from './plugin';
import { useOnce } from '../../utils/share'
import { DotProps, DotResult } from './type';

export const useDot = (props: DotProps): DotResult => {
  const [progress, setProgress] = useState(0)
  const data = useOnce(() => {
    const { plugin, data } = dotPluginFactory({
      onChange: setProgress,
      useDot: props.useDot
    })
    props.carousel.usePlugin(plugin)
    return data
  })

  useEffect(() => {
    data.setUseDot(props.useDot)
  // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [props.useDot])
  return {
    onClick(index) {
      if (!props.dotJump) {
        return
      }
      props.carousel.jump(index)
    },
    getProgress(index) {
      return index === data.getCurrent() ? progress : 0
    }
  }
}