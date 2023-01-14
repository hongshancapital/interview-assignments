#!/bin/bash

npm install || (echo "npm install error" && exit 0)

tsc || ("typescript compile error" && exit 0)

cd build && node index.js
