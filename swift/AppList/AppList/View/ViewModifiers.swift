//
//  ViewModifiers.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import SwiftUI

// MARK: - UI ViewModifiers
struct NavigationStyleModifier: ViewModifier {
    
    var title: String = "App"
    
    func body(content: Content) -> some View {
        content
            .navigationBarTitle(title)
            .ignoresSafeArea(.all, edges: .bottom)
    }
}

// MARK: - Action ViewModifiers

// MARK: - Other ViewModifiers
