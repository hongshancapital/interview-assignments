#include <napi.h>
#include "snowFlake.cc"
#include <string>

std::string randstr(int64 num)
{
  std::string s;
  int remainder;
  const char *dic = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  // 为了确保长度不超过8位，对62^8取模。 
  num = num%218340105584896;
  while(num)
  {
      remainder = num % 62;
      s += dic[remainder];
      num /= 62;
  }
  reverse(s.begin(),s.end());
  return s;
}

Napi::String Method(const Napi::CallbackInfo& info) {
  Napi::Env env = info.Env();
  SnowFlake *sf = new SnowFlake();
  struct timeval tv;

  gettimeofday(&tv, NULL);
  const int64 snowFlakeId = sf->getNextId(tv.tv_sec);
  return Napi::String::New(env, randstr(snowFlakeId));
}

Napi::Object Init(Napi::Env env, Napi::Object exports) {
  exports.Set(Napi::String::New(env, "createShortUrlCpp"),
              Napi::Function::New(env, Method));
  return exports;
}

NODE_API_MODULE(addon, Init);