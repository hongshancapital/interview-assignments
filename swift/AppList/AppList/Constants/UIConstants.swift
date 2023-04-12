import Foundation

extension CGFloat {
    
    static let unit: CGFloat = 8
    
    enum Padding {
        static let one = unit
        static let two = unit * 2
    }
    
    enum Length {
        static let one = unit
        static let two = unit * 2
        static let three = unit * 3
        static let four = unit * 4
        static let five = unit * 5
        static let six = unit * 6
        static let seven = unit * 7
        static let eight = unit * 8
        static let nine = unit * 9
        static let ten = unit * 10
    }
    
    enum CornerRadius {
        static let one = unit
        static let two = unit * 2
    }
}

extension Double {
    static let timeInterval: CGFloat = 1
    
    enum Animation {
        static let short = timeInterval / 3
    }
}
