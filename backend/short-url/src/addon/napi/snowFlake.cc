#include <iostream>
#include <unistd.h>
#include <netdb.h>
#include <sys/time.h>
#include <arpa/inet.h>
#include "snowFlake.h"

#define MAX_NEXT 0b1111111111111111L
#define OFFSET 946656000L

SnowFlake::SnowFlake()
{
    m_offset = 0;
    m_last = 0;
    m_shareid = getServerIdAsLong();
    m_shareid &= 0x1f;
}

SnowFlake::~SnowFlake()
{

}

int64 SnowFlake::getServerIdAsLong()
{
    struct hostent *host = NULL;
    char name[256];
    char szIp[32];
    int64 lIp;
    const char *ret = NULL;

    gethostname(name, sizeof(name));

    host = gethostbyname(name);

    ret = inet_ntop(host->h_addrtype, host->h_addr_list[0], szIp, sizeof(szIp));

    if(ret == NULL)
    {
        std::cout << "hostname transform to ip failed" << std::endl;

        return -1;
    }

    lIp = htonl(inet_addr(szIp));

    return lIp;
}

int64 SnowFlake::getNextId(int64 epoch)
{
    int64 next;

    if(epoch < m_last)
    {
        std::cout << "clock is back: " << epoch << " from previous: " << m_last << std::endl;

        epoch = m_last;
    }

    if(m_last != epoch)
    {
        m_last = epoch;
        m_offset = 0;
    }

    m_offset++;

    next = m_offset & MAX_NEXT;

    if(next == 0)
    {
        std::cout << "maximum id reached in 1 second in epoch: " << std::endl;

        return getNextId(epoch+1);
    }

    return generateId(epoch, next, m_shareid);
}

int64 SnowFlake::generateId(int64 epoch, int64 next, int64 shareid)
{
    return ((epoch-OFFSET) << 21 | (next << 5) | shareid);
}
