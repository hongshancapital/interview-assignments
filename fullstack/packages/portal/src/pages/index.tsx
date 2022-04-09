import { useState } from "react"
import { getShortUrl } from "@/api/data"
import { classnames } from "@/utils/classnames"
import { isPristine, URL_REGEX, useFormState } from "@/utils/useForm"

const IndexPage = () => {
  const [shortUrl, setShortUrl] = useState('')
  const [formState, input] = useFormState();
  const getUrl = async () => {
    const long = await getShortUrl(formState.values.url)
    long && setShortUrl(long)
  }
  return <div className="container h-full pb-80 mx-auto flex flex-col justify-center items-center">
    <h2 className="text-4xl mb-6">短域名</h2>
    <div className="flex flex-col">
      <div className="flex flex-1">
        <textarea
          {...input.tel({
            name: 'url',
            validateOnBlur: true,
            validate: (value) => {
              if (!value.trim()) {
                return '请输入长域名';
              }
              if (!URL_REGEX.test(value)) {
                return '域名不正确';
              }
            },
          })}
          required
          className={classnames(
            'md:w-80',
            'bg-gray-100 px-3 py-2',
            'outline-0 border-solid border-2 border-green-600',
            'focus:border-green-500 focus:bg-white focus:ring-0'
          )}
          placeholder='请输入长域名' />
        <button 
          className={classnames(
            'px-6 py-3 bg-green-600 text-xl text-white',
            'focus:bg-green-500 focus:ring-0',
            'disabled:bg-gray-300',
          )}
          disabled={isPristine(formState, [])}
          onClick={getUrl}>生成</button>
      </div>
      <div className="flex-1">
        <label className='label'>
          <span className='label-text-alt text-red-600'>{formState.errors.url}</span>
        </label>
      </div>
    </div>
    <a target="_blank" href={shortUrl} className="h-10 p-3">
      {shortUrl && `短域名为：${shortUrl}`}
    </a>
  </div>
}



export default IndexPage