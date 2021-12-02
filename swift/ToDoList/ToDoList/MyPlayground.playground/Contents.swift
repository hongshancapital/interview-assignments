import UIKit

// 0 1 1 2 3 5 8 13 21 34
func createArray(_ count: Int = 100) -> [Int] {
    var last = 0
    var new = 1
    var sum = 0
    var count = count
    var data: [Int] = [last, new]
    while count - 2 > 0 {
        sum = last + new
        last = new
        new = sum
        // count - 1
        data.append(sum)
        count -= 1
    }
    return data
}

let array = createArray()
print(array[3])
