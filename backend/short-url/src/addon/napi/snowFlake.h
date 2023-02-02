#ifndef _SNOW_FLAKE_H_
#define _SNOW_FLAKE_H_

#include <string>
#include <stdint.h>

typedef long long int64;

class SnowFlake {
public:
    SnowFlake();
    ~SnowFlake();
    int64 getServerIdAsLong();
    int64 getNextId(int64 epoch);

private:
    int64 generateId(int64 epoch, int64 next, int64 shareid);
    int64 m_offset;
    int64 m_last;
    int64 m_shareid;
};

#endif