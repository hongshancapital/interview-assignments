import React from 'react'

function OtherComponent(): React.ReactElement {
  return <div>OtherComponent</div>
}

export default React.memo(OtherComponent)
