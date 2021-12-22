package store

import (
	"fmt"
	"testing"
)

func TestAdd(t *testing.T) {
	d := GetDomainDao()
	fmt.Println(d.Add("12345"))
	fmt.Println(d.Add("12346"))
	fmt.Println(d.GetByLongUrl("12345"))
	fmt.Println(d.GetByShortUrl("00000001"))
}
