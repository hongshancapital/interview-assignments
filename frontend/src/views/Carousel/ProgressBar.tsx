interface ProgressStyle{
  width: string
  animation?: string
}

const ProgressBar = ({length, curIndex}: {length: number, curIndex:number}) => {
  let array = Array(length).fill(0)
  return <div className="progress-wrap">
    {
      array.map((item, i) => {
        let grayStyle:ProgressStyle = {width: '100%'}
        let blueStyle:ProgressStyle = {width: '0'}
        if(i === curIndex) {
          blueStyle.animation = 'progress 2s forwards'
          grayStyle.animation = 'progress 2s forwards reverse'
        }
        return <div className="progress-item" key={i}>
          <div className="progress-end" style={blueStyle}></div>
          <div className="progress-begin" style={grayStyle}></div>
        </div>
      }) 
    }
  </div>
}

export default ProgressBar