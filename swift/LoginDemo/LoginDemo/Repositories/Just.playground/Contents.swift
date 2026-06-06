import UIKit
import Combine

class someVieModel: ObservableObject {
    var someNumber: Int = 0
    init() {
        Just(1)
            .assign(to: \.someNumber, on: self)
    }
}

var svm = someVieModel()


