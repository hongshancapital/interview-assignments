//
//  RequestMethod.swift
//  SwiftUIApp
//
//  Created by Univex on 2022/5/2.
//

import Foundation


public struct RequsetMethod: RawRepresentable, Equatable, Hashable {

    /// `GET` method.
    public static let get = RequsetMethod(rawValue: "GET")

    /// `POST` method.
    public static let post = RequsetMethod(rawValue: "POST")

    public let rawValue: String

    public init(rawValue: String) {
        self.rawValue = rawValue
    }
}
