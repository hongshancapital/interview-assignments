//
//  ContentView.swift
//  Demo
//
//  Created by 葬花桥 on 2023/3/15.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        HomePageView(viewModel: .init())
    }
}

#if DEBUG
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
#endif
