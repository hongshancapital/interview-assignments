package store

import (
	"short_domain/util"
	"sync"
)

type DomainItem struct {
	LongUrl  string
	ShortUrl string
}

type DomainDao struct {
	sync.RWMutex
	counter     int64
	longToShort map[string]string
	shortToLong map[string]string
}

var d *DomainDao = nil
var doOnce sync.Once

func GetDomainDao() *DomainDao {
	if d == nil {
		doOnce.Do(func() {
			d = new(DomainDao)
			d.longToShort = make(map[string]string, 10000)
			d.shortToLong = make(map[string]string, 10000)
			d.counter = 0
		})
	}
	return d
}

func (d *DomainDao) Add(longUrl string) (string, error) {
	d.Lock()
	defer d.Unlock()
	// 查询有没有，有直接返回
	if str, ok := d.longToShort[longUrl]; ok {
		return str, nil
	}
	d.counter = d.counter + 1
	shortCode, err := util.IntTo62(d.counter)
	if err != nil {
		return "", err
	}
	d.longToShort[longUrl] = shortCode
	d.shortToLong[shortCode] = longUrl
	return shortCode, nil
}

func (d *DomainDao) GetByLongUrl(longUrl string) string {
	d.RLock()
	defer d.RUnlock()
	str := d.longToShort[longUrl]
	return str
}

func (d *DomainDao) GetByShortUrl(shortUrl string) string {
	d.RLock()
	defer d.RUnlock()
	str := d.shortToLong[shortUrl]
	return str
}
