//
//  Failures.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/15.
//

import Foundation

class Failure: Error {}

class ServerFailure: Failure {}

class CacheFailure: Failure {}
