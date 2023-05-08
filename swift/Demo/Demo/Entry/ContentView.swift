//
//  ContentView.swift
//  Demo
//
//  Created by jyt on 2023/3/21.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView {
            AppListView()
                .navigationTitle("App")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
