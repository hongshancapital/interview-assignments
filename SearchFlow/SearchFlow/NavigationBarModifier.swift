//
//  NavigationBarModifier.swift
//  SearchFlow
//
//  Created by evan on 2021/1/8.
//

import SwiftUI

struct NavigationBarModifier : ViewModifier {
    private let backgroundColor: Color
    
    init(backgroundColor: Color?) {
        self.backgroundColor = backgroundColor ?? .white
        let coloredAppearance = UINavigationBarAppearance()
        coloredAppearance.configureWithTransparentBackground()
        coloredAppearance.backgroundColor = .clear
        coloredAppearance.titleTextAttributes = [ .foregroundColor: UIColor.black ]
        coloredAppearance.largeTitleTextAttributes = [ .foregroundColor: UIColor.black ]
        
        UINavigationBar.appearance().standardAppearance = coloredAppearance
        UINavigationBar.appearance().compactAppearance = coloredAppearance
        UINavigationBar.appearance().scrollEdgeAppearance = coloredAppearance
        UINavigationBar.appearance().tintColor = .white
    }
    
    func body(content: Content) -> some View {
        ZStack{
            content
            VStack {
                GeometryReader { geometry in
                    backgroundColor
                        .frame(height: geometry.safeAreaInsets.top)
                        .edgesIgnoringSafeArea(.top)
                    Spacer()
                }
            }
        }
    }
}

extension View {
    func navigationBarColor(_ backgroundColor: Color?) -> some View {
        self.modifier(NavigationBarModifier(backgroundColor: backgroundColor))
    }
}
