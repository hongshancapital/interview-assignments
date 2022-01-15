package service

import "short_domain/store"

type ShortUrlService struct {
	d *store.DomainDao
}

func NewShortUrlService() *ShortUrlService {
	return &ShortUrlService{
		d: store.GetDomainDao(),
	}
}

func (s *ShortUrlService) AddLongUrl(strUrl string) (string, error) {
	shortUlr := s.d.GetByLongUrl(strUrl)
	if shortUlr != "" {
		return shortUlr, nil
	}
	var err error = nil
	shortUlr, err = s.d.Add(strUrl)
	return shortUlr, err
}

func (s *ShortUrlService) GetByShortUrl(strUrl string) string {
	if len(strUrl) != 8 {
		return ""
	}
	return s.d.GetByShortUrl(strUrl)
}
