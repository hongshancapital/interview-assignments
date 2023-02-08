import { requestCreate, ShortRowType } from '../request'
import {
  useMutation,
} from '@tanstack/react-query'
import { Input } from 'antd'
import { useCallback } from 'react';

const { Search } = Input;

export default function UrlTable() {
  const mutation = useMutation<ShortRowType, Error, string>((url) => {
    return requestCreate('create', { url })
  })

  const onCreate = useCallback((url: string) => {
    mutation.mutate(url)
  }, [])

  return (
    <>
      <p>短域名创建</p>
      <Search
        placeholder="input origin url"
        allowClear
        enterButton="Create"
        size="large"
        onSearch={onCreate}
      />

      <div>
        {mutation.isLoading ? (
          'Adding ...'
        ) : (
          <>
            {mutation.isError ? (
              <div>An error occurred: {mutation.error.message}</div>
            ) : null}

            {mutation.isSuccess ? <div>create success</div> : null}
          </>
        )}
      </div>
    </>
  )
}
