import { requestSearch, ShortRowType } from '../request'
import {
  useQuery,
} from '@tanstack/react-query'
import { Input } from 'antd'
import { useState } from 'react';

const { Search } = Input;

export default function UrlTable() {
  const [search, setSearch] = useState('')
  const {
    isSuccess,
    isError,
    data,
    error,
    isLoading,
    isFetching
  } = useQuery<ShortRowType, Error>(['search', search], () => (
    requestSearch(`search/${search}`)
  ), {
    enabled: !!search,
  })

  const long_url = isSuccess && data?.data?.long_url

  return (
    <>
      <p>短域名读取</p>
      <Search
        placeholder="input search text"
        allowClear
        enterButton="Search"
        size="large"
        onSearch={setSearch}
      />

      <div>
        {(isLoading && isFetching) ? (
          'Search ...'
        ) : (
          <>
            {isError ? (
              <div>An error occurred: {error.message}</div>
            ) : null}

            {isSuccess ? <div>search success {long_url}</div> : null}
          </>
        )}
      </div>
    </>
  )
}
