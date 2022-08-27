//
//  ContentView.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView {
            AppStoreView()
        }
    }
}

#if DEBUG
struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
#endif
