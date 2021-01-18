package main

import (
	"bufio"
	"flag"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type line struct {
	content string
	index   int
}

func partition(a []line, lo, hi int) int {
	p := a[hi].index
	for j := lo; j < hi; j++ {
		if a[j].index < p {
			a[j], a[lo] = a[lo], a[j]
			lo++
		}
	}

	a[lo], a[hi] = a[hi], a[lo]
	return lo
}

func quickSort(a []line, lo, hi int) {
	if lo > hi {
		return
	}

	p := partition(a, lo, hi)
	quickSort(a, lo, p-1)
	quickSort(a, p+1, hi)
}

func parseFile(filename string) []line {
	file, err := os.Open(filename)
	if err != nil {
		return nil
	}

	defer file.Close()
	reader := bufio.NewReader(file)
	totalLine := 0

	for {
		_, err := reader.ReadString('\n')
		if err != nil {
			break
		}
		totalLine++
	}
	lines := make([]line, totalLine)

	file.Seek(0, 0)
	index := 0
	for {
		l, err := reader.ReadString('\n')
		if err != nil {
			break
		}
		l = l[:len(l)-1]
		partation := strings.Split(l, " ")
		lines[index].content = partation[0]
		lines[index].index, _ = strconv.Atoi(partation[1])
		index++
	}
	return lines
}

func printLines(lines []line) {
	for i := 0; i < len(lines); i++ {
		fmt.Printf("%s %d\n", lines[i].content, +lines[i].index)
	}
}

func main() {
	var filename string
	flag.StringVar(&filename, "f", "lines.txt", "file to sort")
	flag.Parse()

	lines := parseFile(filename)
	if lines == nil {
		return
	}

	fmt.Println("befor sort:")
	fmt.Println("-----------------------------------------------")
	printLines(lines)
	fmt.Println("-----------------------------------------------")

	quickSort(lines, 0, len(lines)-1)

	fmt.Println("after sort:")
	fmt.Println("-----------------------------------------------")
	printLines(lines)
	fmt.Println("-----------------------------------------------")
}
