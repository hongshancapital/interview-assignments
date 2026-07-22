//
//  ToDoListAPIError.swift
//  InterviewAssignment (iOS)
//
//  Created by Vic Zhang on 2021/12/10.
//

import Foundation
import SwiftUI

enum ToDoListAPIError  : Error {
    case invalidHttpResponse
    case responseError(statusCode: Int, data: Data)
    case urlError(URLError)
    case decodeError(Error)
}
