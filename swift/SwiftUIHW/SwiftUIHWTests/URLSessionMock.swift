//
//  URLSessionMock.swift
//  SwiftUIHWTests
//
//  Created by 施治昂 on 4/17/22.
//

import Foundation
import UIKit
@testable import SwiftUIHW

final class URLSessionMock: URLSessionProtocol {
    func requestData(from url: URL) async throws -> (Data, URLResponse) {
        let d = UIImage(systemName: "heart")!.pngData()!
        let res = HTTPURLResponse(url: URL(string: "http://")!, statusCode: 200, httpVersion: nil, headerFields: nil)
        return (d, res!)
    }
}
