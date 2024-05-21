import {
  QueryClient,
  QueryClientProvider,
} from '@tanstack/react-query'

import UrlTable from '../components/UrlTable'
import UrlSearch from '../components/UrlSearch'
import UrlCreate from '../components/UrlCreate'

const queryClient = new QueryClient()

export default function Home() {
  return (
    <QueryClientProvider client={queryClient}>
      <p>短域名服务</p>
      <UrlTable />
      <UrlSearch />
      <UrlCreate />
    </QueryClientProvider>
  )
}
