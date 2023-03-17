//
//  ContentView.swift
//  Shared
//
//  Created by Ray Tao on 2022/7/12.
//

import SwiftUI


struct ContentView: View {
    @EnvironmentObject var store: Store

    var body: some View {
        AppListRootView()
        .edgesIgnoringSafeArea(.top)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
