import {IProps, ActionProps} from './state'

class Reducer {
  time(state: IProps, {index = 0}: IProps) {
    const { options = [] } = state
    if (options.length <= index) {
      return {
        ...state,
        index: 0
      }
    } else {
      return {
        ...state,
        index
      }
    }
  }
}

const fn: {
  [key: string]: any
} = new Reducer()
export const reducer = (state: IProps, action: ActionProps) => {
  if (fn[action.type]) {
    return fn[action.type](state, action.payload)
  } else {
    throw new Error('缺少数据处理函数，请检查数据')
  }
}