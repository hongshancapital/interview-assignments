//
//  Constant.swift
//  AppsDemo
//
//  Created by changcun on 2022/3/6.
//

import Foundation
import UIKit

let preferredPadding: CGFloat = 18

let preferredCornerRadius: CGFloat = 15

enum NetworkError: Error {
    case invalidURL
    case customer(errorMessage: String)
}
