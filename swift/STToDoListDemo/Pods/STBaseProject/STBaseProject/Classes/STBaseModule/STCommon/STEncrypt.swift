//
//  STEncrypt.swift
//  STBaseProject
//
//  Created by stack on 2018/12/22.
//  Copyright © 2018 ST. All rights reserved.
//

import Foundation
import CommonCrypto

public extension String {
    func st_md5() -> String {
        let data = Data(self.utf8)
        let hash = data.withUnsafeBytes { (bytes: UnsafeRawBufferPointer) -> [UInt8] in
            var hash = [UInt8](repeating: 0, count: Int(CC_MD5_DIGEST_LENGTH))
            CC_MD5(bytes.baseAddress, CC_LONG(data.count), &hash)
            return hash
        }
        return hash.map { String(format: "%02x", $0) }.joined()
    }
}
