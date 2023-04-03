import { useState } from "react";
import { LongToShort } from "./LongToShort";
import { ShortToLong } from "./ShortToLong";

function App() {
  return (
    <div>
      <LongToShort />
      <ShortToLong />
    </div>
  );
}

export default App;
