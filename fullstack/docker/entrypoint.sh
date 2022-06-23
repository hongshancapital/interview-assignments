#!/usr/bin/env bash
dockerize -wait tcp://mysql:3306 -timeout 60s
npx prisma generate
npm run dev