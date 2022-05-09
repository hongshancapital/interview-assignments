//
//  HomeState.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/8.
//

import Foundation
import SwiftUIFlux

struct HomeState: FluxState, Codable {
    var orderItems: [AppItemVM] = []
    var distinctItems: [Int : AppItemVM] = [:]
}
