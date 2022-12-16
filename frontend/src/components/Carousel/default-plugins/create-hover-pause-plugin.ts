import { PluginContext } from '../types';
import { EventPlugin } from '../event-plugin-handler';
const createHoverPausePlugin = (): EventPlugin<PluginContext> => {
  let oriPausedState = false;
  return {
    onMouseEnter: (e, context) => {
      const { setPaused } = context;
      setPaused(oldState=>{
        oriPausedState = oldState;
        return true;
      });
    },
    onMouseLeave: (e, context) => {
      const { setPaused } = context;
      setPaused(oriPausedState);
    },
  };
};
export default createHoverPausePlugin;