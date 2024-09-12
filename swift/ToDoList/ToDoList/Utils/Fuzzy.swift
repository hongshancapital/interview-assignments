//
//  Fuzzy.swift
//  ToDoList
//
//  Created by 炎檬 on 2022/1/17.
//

class Fuzzy {
    class func search(needle: String, haystack: String) -> Bool {
        guard needle.count <= haystack.count else {
            return false
        }
        
        if needle == haystack {
            return true
        }
        
        var needleIdx = needle.startIndex
        var haystackIdx = haystack.startIndex
        
        while needleIdx != needle.endIndex {
            if haystackIdx == haystack.endIndex {
                return false
            }
            if needle[needleIdx] == haystack[haystackIdx] {
                needleIdx = needle.index(after: needleIdx)
            }
            haystackIdx = haystack.index(after: haystackIdx)
        }
        
        return true
    }
}
