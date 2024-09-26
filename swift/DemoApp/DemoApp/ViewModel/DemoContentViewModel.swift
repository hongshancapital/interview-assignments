//
//  DemoContentViewModel.swift
//  DemoApp
//
//  Created by Meteor 丶Shower on 2022/5/19.
//

import Foundation
import Combine
import SwiftUI


class DemoContentViewModel: ObservableObject {
    @Published var results: [ContentResult] = []
    init() {
        self.results = contentData.results
    }
}


