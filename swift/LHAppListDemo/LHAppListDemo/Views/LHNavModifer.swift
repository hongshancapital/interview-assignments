//
//  LHNavModifer.swift
//  LHAppListDemo
//
//  Created by lzh on 2022/3/29.
//

import SwiftUI

struct LHNavModifier: ViewModifier {
    var title: String = "App"

    func body(content: Content) -> some View {
        content.navigationBarTitle(title)
            .ignoresSafeArea(.all, edges: .bottom)
    }
}
