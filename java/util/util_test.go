package util

import (
	"fmt"
	"testing"
)

func TestIntTo62(t *testing.T) {
	var id int64 = 63
	fmt.Println(IntTo62(id))
	fmt.Println(IntTo62(62))
}
