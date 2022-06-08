# Simple React Carousel Component Documentaion

## Features

This is a simple react typescript carousel component.

Pass your DOM elements as children of this component with some props, you will get a carousel.

## Usage

1. You will need a React TypeScript project first.
2. Copy `components/Carousel.tsx` & `components/Carousel.css` into your project.
3. `import Carousel from './components/Carousel'`.
4. Pass some config as props if you like.
5. `yarn start`.
6. Have fun!

The code will be like:

``` typescript
import React from 'react'
import Carousel from './components/Carousel'

function App () {
  return <div className="App">
    <Carousel>
      <div>
        <img src="assets/iphone.png" alt="img"/>
        <p>iPhone</p>
      </div>
      <div>
        <img src="assets/tablet.png" alt="img"/>
        <p>Tablet</p>
      </div>
      <div>
        <img src="assets/airpods.png" alt="img"/>
        <p>Airpods</p>
      </div>
    </Carousel>
  </div>
}

export default App
```

Or you can check the `App.tsx` for demo.

## Props

| Key            | Value      | Default   | Description                                                                       |
|----------------|------------|-----------|-----------------------------------------------------------------------------------|
| children       | `Elements` | `null`    | Carousel slide items,                                                             |
| autoplayTime   | `Number`   | `2000` ms | Carousel auto play time. Set to `0` to disable autoplay.                          |
| transitionTime | `Number`   | `500` ms  | Carousel slide transition time.                                                   |
| infinity       | `Boolean`  | `true`    | If set to `true`, the slide will restart from first item when it reaches the end. |
| showArrows     | `Boolean`  | `true`    | The visibility of switch control arrow.                                           |
| showStatus     | `Boolean`  | `true`    | The visibility of status bar.                                                     |
| onChange       | `Function` | `noop`    | On change hook function.                                                          |
| defaultIndex   | `Number`   | `0`       | Default index when rendered to page.                                              |

## Todo

```typescript

// 1. Use `Swipe` to support touch event.
// 2. Support different status type (bar or dot).
// 3. Support more hook function.
// 4. Add some test case...

```
