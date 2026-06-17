//
//  String+.swift.swift
//  Network
//
//  Created by shencong on 2022/6/10.
//

import Foundation

extension String {
    func toData() -> Data {
        return self.data(using: Encoding.utf8) ?? Data()
    }
}
