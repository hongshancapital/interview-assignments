//
//  String+Extenstion.swift
//  SwiftUIDemo
//
//  Created by weizhao on 2021/10/17.
//  Copyright © 2021 weizhao. All rights reserved.
//

import Foundation

extension String {
    
    subscript(_ indexs: ClosedRange<Int>) -> String {
        if self.lengthOfBytes(using: Encoding.utf8) < 2 {
            return ""
        }
        let beginIndex = index(startIndex, offsetBy: indexs.lowerBound)
        let endIndex = index(startIndex, offsetBy: indexs.upperBound)
        if beginIndex < self.startIndex || beginIndex > self.endIndex || endIndex < self.startIndex || endIndex > self.endIndex {
            return self
        }
        return String(self[beginIndex...endIndex])
    }
    
    subscript(_ indexs: Range<Int>) -> String {
        if self.lengthOfBytes(using: Encoding.utf8) < 2  {
            return ""
        }
        let beginIndex = index(startIndex, offsetBy: indexs.lowerBound)
        let endIndex = index(startIndex, offsetBy: indexs.upperBound)
        if beginIndex < self.startIndex || beginIndex > self.endIndex || endIndex < self.startIndex || endIndex > self.endIndex {
            return self
        }
        return String(self[beginIndex..<endIndex])
    }
    
    subscript(_ indexs: PartialRangeThrough<Int>) -> String {
        if self.lengthOfBytes(using: Encoding.utf8) < 2 {
            return ""
        }
        let endIndex = index(startIndex, offsetBy: indexs.upperBound)
        if endIndex >= self.endIndex {
            return self
        }
        return String(self[startIndex...endIndex])
    }
    
    subscript(_ indexs: PartialRangeFrom<Int>) -> String {
        if self.lengthOfBytes(using: Encoding.utf8) < 2  {
            return ""
        }
        let beginIndex = index(startIndex, offsetBy: indexs.lowerBound)
        if beginIndex < self.startIndex {
            return self
        }
        return String(self[beginIndex..<endIndex])
    }
    
    subscript(_ indexs: PartialRangeUpTo<Int>) -> String {
        if self.lengthOfBytes(using: Encoding.utf8) < 2  {
            return ""
        }
        let endIndex = index(startIndex, offsetBy: indexs.upperBound)
        if endIndex > self.endIndex {
            return self
        }
        return String(self[startIndex..<endIndex])
    }
    
}


