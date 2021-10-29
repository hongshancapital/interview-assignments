import React, {useEffect, useRef, useState} from 'react'
import {TransitionState, useCSSTransition, skeduler} from '../hook/useCSSTransition'
import { render } from '@testing-library/react'
import { AsyncUtil } from '../util/AsyncUtil'
import { act } from 'react-dom/test-utils'


/**
 * 测试动画库
 * @param param0 
 * @returns 
 */

const TestTransition = ({tests, loops, enabled = true} : {
  tests : string[],
  loops : number,
  enabled? : boolean
}) => {

  const loopTimes = useRef(loops)

  const [,style] = useCSSTransition({
    wait : 20,
    duration : 10,
    enabled,
    transitions : {
      prepare : {
        marginLeft : 10
      },
      enter : {
        marginLeft : 20,
      },
      leave : {
        marginLeft : 30
      }
    },
    loop : loops > 1,
    initialStyle : {marginLeft : loops},
    on(topic, context) {
      tests.push('topic=' + TransitionState[topic]+ " " + style.marginLeft)
      if(topic === TransitionState.FINISH) {
        if( (--loopTimes.current) === 0) {
          context?.cancel()
        }
      }
    },
  })

  return <div>{style.marginLeft}</div>
}

test("scheduler", (done) => {
  skeduler(() => {
    done()
  }, "nextTick")
})

test("scheduler02", (done) => {
  skeduler(() => {
    done()
  }, "immediate")
})

test("scheduler03", (done) => {
  skeduler(() => {
    done()
  }, 10)
})


test("basic", async () => {
  const tests : string[] = []
  const { findByText } = render(
    <TestTransition tests={tests} loops={1} />
  )
  expect(await findByText('30')).toBeInTheDocument();
  expect(tests).toEqual([
    'topic=START 1',
    'topic=PREPARE 10',
    'topic=ENTER 20',
    'topic=LEAVE 30',
    'topic=FINISH 30'
  ])
})

test("loop", async () => {
  const tests : string[] = []
  const { findByText } = render(
    <TestTransition tests={tests} loops={2} />
  )
  expect(await findByText('30')).toBeInTheDocument();
  act(() => {
    AsyncUtil.wait(100)
    .then(() => {
      expect(tests).toEqual([
        "topic=START 2",
        "topic=PREPARE 10",
        "topic=ENTER 20",
        "topic=LEAVE 30",
        "topic=FINISH 30",
        "topic=START 2",
        "topic=PREPARE 10",
        "topic=ENTER 20",
        "topic=LEAVE 30",
        "topic=FINISH 30",
      ])
    })
  })

})


const TestEnabledComponent = ({
  tests,
}: {
  tests: string[]

}) => {
  const [enabled, setEnabled] = useState(false)
  useEffect(() => {
    setTimeout(() => {
      setEnabled(true)
    }, 0)
  })

  return <TestTransition tests={tests} loops={1} enabled={enabled} />
}

test('enabled', async () => {
  const tests : string[] = []
  const { findByText } = render(
    <TestEnabledComponent tests={tests} />
  ) 
  expect(await findByText('30')).toBeInTheDocument()
})
